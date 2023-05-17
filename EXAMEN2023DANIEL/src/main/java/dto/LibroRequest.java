package dto;

public record LibroRequest(
		

		Long idlibro,
		String titulo,
		String descripcion, 
		int paginas, 
		String edicion, 
		Long idautor, 
		Long ideditorial

) {

}
