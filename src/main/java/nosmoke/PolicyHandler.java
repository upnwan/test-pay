package nosmoke;

import com.sun.tools.javac.resources.ct;
import nosmoke.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    PayRepository payRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPointPaid_UpdatePayId(@Payload PointPaid pointPaid){

        if(pointPaid.isMe()){
            System.out.println("##### listener UpdateStatus : " + pointPaid.toJson());
            Optional<Pay> payOptional = payRepository.findById(pointPaid.getId());
            Pay pay = payOptional.get();
            pay.setPayId(pointPaid.getCheckInId());

            payRepository.save(pay);
        }
    }

}
