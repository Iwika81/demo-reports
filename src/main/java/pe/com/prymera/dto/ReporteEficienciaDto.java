package pe.com.prymera.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteEficienciaDto implements Serializable {

	private static final long serialVersionUID = -6793251569543235026L;

	private String nombre;
	private int nuevoDesembolzo;
	private BigDecimal nuevoMonto;
	private int campaniaDesembolzo;
	private BigDecimal campaniaMonto;
	private int otroDesembolzo;
	private BigDecimal otroMonto;
	private int carteraClientes;
	private int carteraCreditos;
	private BigDecimal carteraSaldos;
	private int carteraAnteriorClientes;
	private int carteraAnteriorCreditos;
	private BigDecimal carteraAnteriorSaldos;
}
