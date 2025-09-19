package cloud.onion.cms.config.sign;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestHeader {
   private String sign ;
   private Long timestamp ;
   private String nonce;
}