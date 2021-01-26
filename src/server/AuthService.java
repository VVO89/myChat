package server;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

public interface AuthService  {
    void start();
    String getNickByLoginAndPass(String login, String password);
    void stop();
}
