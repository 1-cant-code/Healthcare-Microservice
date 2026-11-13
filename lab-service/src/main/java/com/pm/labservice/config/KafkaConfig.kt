package com.pm.labservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
	public static String RETENTION_BYTES_CONFIG = "524288000";
	public static String RETENTION_MS_CONFIG = "86400000";
	@Bean
	public NewTopic labTestCompleted() {

		return TopicBuilder.name("lab-test-completed")
				.partitions(3)
				.replicas(3)
				.config(TopicConfig.RETENTION_MS_CONFIG, RETENTION_MS_CONFIG)
				.config(TopicConfig.RETENTION_BYTES_CONFIG, RETENTION_BYTES_CONFIG)
				.build();
	}

	@Bean
	public NewTopic labTestFailed() {
		return TopicBuilder.name("lab-test-failed")
				.partitions(3)
				.replicas(3)
				.config(TopicConfig.RETENTION_MS_CONFIG, RETENTION_MS_CONFIG)
				.config(TopicConfig.RETENTION_BYTES_CONFIG, RETENTION_BYTES_CONFIG)
				.build();
	}
	@Bean
	public NewTopic labTestSucceed() {
		return TopicBuilder.name("lab-test-succeed")
				.partitions(3)
				.replicas(3)
				.config(TopicConfig.RETENTION_MS_CONFIG, "86400000")
				.config(TopicConfig.RETENTION_BYTES_CONFIG, "524288000")
				.build();
	}
}
