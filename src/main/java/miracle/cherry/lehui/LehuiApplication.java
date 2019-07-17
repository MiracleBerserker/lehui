package miracle.cherry.lehui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class LehuiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LehuiApplication.class, args);
    }

}
