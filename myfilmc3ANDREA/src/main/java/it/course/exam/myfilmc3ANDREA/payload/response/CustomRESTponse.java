package it.course.exam.myfilmc3ANDREA.payload.response;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CustomRESTponse {
	
	private Instant timestamp;
	private int status;
	private String error;
	private Object message;
	private String path;
	
	public CustomRESTponse(int status, String error, Object message, HttpServletRequest request) {
		super();
		timestamp = Instant.now();
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = request.getRequestURI();
	}
	
}
