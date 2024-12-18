package com.ahmedferjani.music_metadata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class MusicMetadataApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicMetadataApplication.class, args);
	}

}
