package tipoDados;

import java.sql.Date;
import java.sql.Time;

public class Doacao {
    
    private Integer codigo;
    private Integer idDoador;
    private String nomeDoador;
    private Date data;
    private Time hora;
    private Boolean anemia;
    private Float peso;
    private Float pulso;
    private Float temperatura;
    private String pressao;

    public String getNomeDoador() {
        return nomeDoador;
    }

    public void setNomeDoador(String nomeDoador) {
        this.nomeDoador = nomeDoador;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getIdDoador() {
        return idDoador;
    }

    public void setIdDoador(Integer idDoador) {
        this.idDoador = idDoador;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Boolean getAnemia() {
        return anemia;
    }

    public void setAnemia(Boolean anemia) {
        this.anemia = anemia;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getPulso() {
        return pulso;
    }

    public void setPulso(Float pulso) {
        this.pulso = pulso;
    }

    public Float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public String getPressao() {
        return pressao;
    }

    public void setPressao(String pressao) {
        this.pressao = pressao;
    }
    
    
}
