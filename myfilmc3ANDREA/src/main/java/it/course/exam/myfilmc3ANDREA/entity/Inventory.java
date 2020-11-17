package it.course.exam.myfilmc3ANDREA.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INVENTORY")
@Data @AllArgsConstructor @NoArgsConstructor
public class Inventory {
	
	@EmbeddedId
	private InventoryId inventoryId;

}
