package pe.com.prymera.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnalistaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre;
	private String perfil;
	private int nuevoDesembolzo;
	private BigDecimal nuevoMonto;
	private int campaniaDesembolzo;
	private BigDecimal campaniaMonto;
	private int otroDesembolzo;
	private BigDecimal otroMonto;
	private int carteraClientes;
	private int carteraCreditos;
	private BigDecimal carteraSaldo;
	private int carteraAnteriorClientes;
	private int carteraAnteriorCreditos;
	private BigDecimal carteraAnteriorSaldo;
}
