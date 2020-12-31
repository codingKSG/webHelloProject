package com.cos.hello.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private int id;
	private String title;
	private String content;
	private int userId; //fk
}
