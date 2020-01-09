package pe.com.prymera.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EficienciaVisitaAnalistaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre;
	private int periodoAnteriorVisitasRegistradas;
	private int periodoAnteriorClientesNuevos;
	private int periodoAnteriorDesembolsosClientesNuevos;
	private BigDecimal periodoAnteriorMontosClientesNuevos;
	private int periodoActualVisitasRegistradas;
	private int periodoActualClientesNuevos;
	private int periodoActualDesembolsosClientesNuevos;
	private BigDecimal periodoActualMontosClientesNuevos;
}
