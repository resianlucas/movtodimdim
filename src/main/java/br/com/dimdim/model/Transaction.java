package br.com.dimdim.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_transaction")
public class Transaction {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Size(max = 120, message = "A descrição deve até 120 caracteres.")
	@NotBlank(message = "A descripção é obrigatória.")
	private String description;
	@Min(value = 1, message = "A quantidade precisa ser maior do que zero.")
	private BigDecimal value;
	@DateTimeFormat (pattern="yyyy-MM-dd")
	private Date date;
	@Enumerated(EnumType.STRING)
	private TransactionType type;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
