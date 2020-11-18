package it.course.exam.myfilmc3ANDREA.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class StoreRequest {
	
	@NotBlank
	@Size(max = 10)
	private String storeId;
	
	@NotBlank
	@Size(max = 50)
	private String storeName;

}
