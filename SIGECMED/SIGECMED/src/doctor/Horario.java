/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctor;

import java.sql.Date;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author frani
 */
public class Horario {
    
    private int idHorario;
    private int idDoctor;
    private String intervaloDias;
    private Time horaInicio;
    private Time horaFin;
    private Date fechaInicio;
    private Date fechaFin;
    
    public Horario() {}

    public Horario(int idHorario, int idDoctor, String intervaloDias, Time horaInicio, Time horaFin, Date fechaInicio, Date fechaFin) {
        this.idHorario = idHorario;
        this.idDoctor = idDoctor;
        this.intervaloDias = intervaloDias;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getIntervaloDias() {
        return intervaloDias;
    }

    public void setIntervaloDias(String intervaloDias) {
        this.intervaloDias = intervaloDias;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public String getStringToCalendar() {
        boolean[] dias = {
            intervaloDias.substring(0, 1).equals("1"),
            intervaloDias.substring(1, 2).equals("1"),
            intervaloDias.substring(2, 3).equals("1"),
            intervaloDias.substring(3, 4).equals("1"),
            intervaloDias.substring(4, 5).equals("1")
        };
        String[] codigos = {"MO", "TU", "WE", "TH", "FR"};

        // Une los dias activos con comas: "MO,TU,FR"
        StringBuilder aux = new StringBuilder();
        String separador = "";
        for (int i = 0; i < dias.length; i++) {
            if (dias[i]) {
                aux.append(separador).append(codigos[i]);
                separador = ",";
            }
        }
        return aux.toString();
    }
    
    public int getNumberOfDays() {
        long diff = fechaFin.getTime() - fechaInicio.getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
        int countDays = StringUtils.countMatches(intervaloDias, "1");
        double temp = days / 7.0;
        int semanas = (int) Math.ceil(temp);
        return semanas * countDays;
    }
    
}
