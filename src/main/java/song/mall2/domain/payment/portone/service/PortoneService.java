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
import song.mall2.exception.invalid.exceptions.InvalidPortonePaymentException;

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
            throw new InvalidPortonePaymentException();
        }
    }

    public PortonePaymentsResponse getPortonePayment(String paymentId) {
        try {
            PortonePaymentsResponse payment = client.getPaymentByPaymentId(paymentId, storeId);

            logging(payment);
            return payment;
        } catch (IamportResponseException | IOException ex) {
            throw new InvalidPortonePaymentException();
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
            throw new InvalidPortonePaymentException("취소 예외");
        }
    }

    private void logging(Object response) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        String payment = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        log.info("payment: \n{}", payment);
    }
}
