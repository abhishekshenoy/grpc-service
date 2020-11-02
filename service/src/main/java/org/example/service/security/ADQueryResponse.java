package org.example.service.security;

import lombok.Data;

import java.util.List;

@Data
public class ADQueryResponse {

	private List<String> memberOf;
}