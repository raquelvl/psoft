package terceiro.exemplo.estoquej.dtos;

import terceiro.exemplo.estoquej.entidades.Papel;
import terceiro.exemplo.estoquej.entidades.Usuario;

public class UsuarioDTO {
    private String email;

    private String nomeCompleto;

    private Papel papel = Papel.REGULAR;

    public UsuarioDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "email='" + email + '\'' +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", papel=" + papel +
                '}';
    }

    public static UsuarioDTO from(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getEmail());
        usuarioDTO.setPapel(usuario.getPapel());
        usuarioDTO.setNomeCompleto(usuario.getNomeCompleto());
        return usuarioDTO;
    }
}
