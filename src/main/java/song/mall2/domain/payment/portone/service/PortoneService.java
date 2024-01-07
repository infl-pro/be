package song.mall2.domain.payment.portone.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelRequest;
import com.siot.IamportRestClient.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import song.mall2.exception.portone.exceptions.PortoneAuthenticateException;
import song.mall2.exception.portone.exceptions.PortoneCancellationException;
import song.mall2.exception.portone.exceptions.PortonePaymentException;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortoneService {
    private final IamportClient client;

    @Value("${portone.store-id}")
    private String storeId;
    @Value("${portone.channel-key}")
    private String channelKey;
    @Value("${portone.api-secret}")
    private String apiSecret;

    public AccessTokenResponse authenticate() {
        try {
            AccessTokenResponse accessToken = client.getAccessToken();

            log.info("accessToken: {}", accessToken.getAccessToken());

            return accessToken;
        } catch (IamportResponseException | IOException ex) {
            throw new PortoneAuthenticateException("인증 과정에서 예외가 발생했습니다.");
        }
    }

    public PortonePaymentsResponse getPortonePayment(String paymentId) {
        try {
            PortonePaymentsResponse payment = client.getPaymentByPaymentId(paymentId, storeId);

            logging(payment);
            return payment;
        } catch (IamportResponseException | IOException ex) {
            throw new PortonePaymentException("결제정보를 가져오는 과정에서 예외가 발생했습니다.");
        }
    }

    public CancellationResponse cancel(String paymentId) {
        CancelRequest cancelRequest = new CancelRequest();
        cancelRequest.setReason("cancel");
        try {
            CancellationResponse cancellationResponse = client.cancelPaymentByPaymentId(paymentId, cancelRequest);

            logging(cancellationResponse);
            return cancellationResponse;
        } catch (IamportResponseException | IOException ex) {
            throw new PortoneCancellationException("취소과정에서 예외가 발생했습니다.");
        }
    }

    private void logging(Object response) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        String payment = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        log.info("payment: \n{}", payment);
    }
}
