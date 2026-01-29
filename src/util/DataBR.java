package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public final class DataBR {
    private static final DateTimeFormatter BR =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private DataBR(){}

    /** Tenta parsear dd/MM/aaaa; retorna null se inválida */
    public static LocalDate parse(String ddMMyyyy) {
        try { return LocalDate.parse(ddMMyyyy.trim(), BR); }
        catch (Exception e) { return null; }
    }

    /** Formata LocalDate em dd/MM/aaaa */
    public static String format(LocalDate d) {
        return d == null ? "" : d.format(BR);
    }

    /** ISO yyyy-MM-dd (para gravar no banco) */
    public static String toIso(LocalDate d) {
        return d == null ? null : d.toString();
    }
}
