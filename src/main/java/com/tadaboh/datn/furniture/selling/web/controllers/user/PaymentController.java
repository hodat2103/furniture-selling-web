package com.tadaboh.datn.furniture.selling.web.controllers.user;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.tadaboh.datn.furniture.selling.web.configurations.VNPayConfig;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.PaymentVNPAYResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.TransactionStatusResponse;
import com.tadaboh.datn.furniture.selling.web.enums.PaypalPaymentIntent;
import com.tadaboh.datn.furniture.selling.web.enums.PaypalPaymentMethod;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.repositories.OrderRepository;
//import com.tadaboh.datn.furniture.selling.web.services.impl.PaypalService;
import com.tadaboh.datn.furniture.selling.web.utils.PaypalUrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("${api.prefix}payments")
@RequiredArgsConstructor
public class PaymentController {
    public static final String PAYPAL_SUCCESS_URL = "pay/success";
    public static final String PAYPAL_CANCEL_URL = "pay/cancel";

    private Logger log = LoggerFactory.getLogger(getClass());

//    private final PaypalService paypalService;
    private final OrderRepository orderRepository;

    @GetMapping("/create-payment")
    public ResponseEntity<?> createPayment(
            HttpServletRequest request,
            @RequestParam(value = "final_price") BigDecimal amount,
            @RequestParam(value = "order_id") Long orderId
    ) throws UnsupportedEncodingException {
        String orderType = "other";
//        Integer orderId = 1;
//        amount = amount*2400000;
        amount = amount.multiply(BigDecimal.valueOf(100));
//            String bankCode = req.getParameter("bankCode");;

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
//            String vnp_IpAddr = VNPayConfig.getIpAddress(req);

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderId.toString());
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", VNPayConfig.getIpAddress(request));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;


        PaymentVNPAYResponse paymentVNPAYResponse = PaymentVNPAYResponse.builder()
                .status("OK")
                .message("")
                .url(paymentUrl)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(paymentVNPAYResponse);
    }
    @GetMapping("/check-payment")
    public ResponseEntity<?> transaction(
            @RequestParam(value = "vnp_Amount") BigDecimal amount,
            @RequestParam(value = "vnp_BankCode") String bankCode,
            @RequestParam(value = "vnp_CardType", required = false) String cardType,
            @RequestParam(value = "vnp_OrderInfo") String orderInfo,
            @RequestParam(value = "vnp_PayDate") String payDate,
            @RequestParam(value = "vnp_ResponseCode") String responseCode,
            @RequestParam(value = "vnp_ResponseCode") String tmnCode,
            @RequestParam(value = "vnp_TransactionNo") String transactionNo,
            @RequestParam(value = "vnp_TransactionStatus") String transactionStatus,
            @RequestParam(value = "vnp_TxnRef") String txnRef,
            @RequestParam(value = "vnp_SecureHash") String secureHash
    ) throws DataNotFoundException {
        TransactionStatusResponse transactionStatusResponse = new TransactionStatusResponse();
        // payment successfully
        if (responseCode.equals("00")){
            transactionStatusResponse = TransactionStatusResponse.builder()
                    .status("Accepted")
                    .message("Payment successful")
                    .data("https://sandbox.vnpayment.vn/apis/vnpay-demo/")
                    .build();

            return ResponseEntity.ok(transactionStatusResponse);
        }
        else
        {
            Long orderId = Long.getLong(orderInfo);
//            Order order = this.orderRepository.findById(orderId)
//                    .orElseThrow(() -> new DataNotFoundException(
//                            "" + orderId
//                    ));
//            order.setPaymentMethod("other");
//            order.setStatus(OrderStatusEnum.CANCELLED);
//            this.orderRepository.save(order);
            return ResponseEntity.badRequest().body(TransactionStatusResponse.builder()
                    .status("Failed")
                    .message("Payment failed")
                    .data("localhost:5173")  // Redirect to the homeclient page
                    .build());
        }
    }

//    @PostMapping("pay")
//    public String pay(HttpServletRequest request){
//        String cancelUrl = PaypalUrlUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
//        String successUrl = PaypalUrlUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
//        try {
//            Payment payment = paypalService.createPayment(
//                    4.00,
//                    "USD",
//                    PaypalPaymentMethod.PAYPAL,
//                    PaypalPaymentIntent.SALE,
//                    "payment description",
//                    cancelUrl,
//                    successUrl);
//            for(Links links : payment.getLinks()){
//                if(links.getRel().equals("approval_url")){
//                    return "redirect:" + links.getHref();
//                }
//            }
//        } catch (PayPalRESTException e) {
//            log.error(e.getMessage());
//        }
//        return "redirect:/";
//    }
//
//    @GetMapping(PAYPAL_CANCEL_URL)
//    public String cancelPay(){
//        return "cancel";
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value = PAYPAL_SUCCESS_URL)
//    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
//        try {
//            Payment payment = paypalService.executePayment(paymentId, payerId);
//            if(payment.getState().equals("approved")){
//                return "success";
//            }
//        } catch (PayPalRESTException e) {
//            log.error(e.getMessage());
//        }
//        return "redirect:/";
//    }

}
