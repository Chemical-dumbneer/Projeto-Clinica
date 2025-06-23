package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfiguracoesSistema {

    private static final Properties props = new Properties();

    static {
        try (FileInputStream entrada = new FileInputStream("config.properties")) {
            props.load(entrada);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo de configuração:");
            e.printStackTrace();
        }
    }

    public static String getCaminhoImagens() {
        return props.getProperty("caminhoImagens");
    }

    // Se quiser validar se o caminho existe
    public static boolean caminhoImagensEhValido() {
        String caminho = getCaminhoImagens();
        return caminho != null && new java.io.File(caminho).exists();
    }
}
