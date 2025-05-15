package asaromi.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ResearchApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResearchApiApplication.class, args);
		BigDecimal value = new BigDecimal(6974116.76219121);
		System.out.println("Want to try something:" + value.doubleValue());

		System.out.println("Current JVM Timezone: " + java.util.TimeZone.getDefault().getID());
	}

}
