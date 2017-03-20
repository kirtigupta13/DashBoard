package com.scpdashboard.controllers;

import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.connection.ChannelOutputStream;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.session.SessionOutputReader;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;

public class test {

    public void connect(){
        final String username = "kg048707";
        final String hostname = "fork";
        final String password = "go";
        try {

            final SshClient ssh = new SshClient();  
            ssh.connect("fork", new IgnoreHostKeyVerification());
//reacgtor built -> 
            /* Authenticate.
             * If you get an IOException saying something like
             * "Authentication method password not supported by the server at this stage."
             */
            final PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
            pwd.setUsername(username);
            pwd.setPassword(password);

            final int result = ssh.authenticate(pwd);

            if (result != AuthenticationProtocolState.COMPLETE){                
                System.out.println("Login to " + hostname + " " + username + "/" + password + " failed");
            } 

            System.out.println("Sucesses : "+result+" code :"+  AuthenticationProtocolState.COMPLETE);
            /////////////
            final SessionChannelClient session = ssh.openSessionChannel();
            final SessionOutputReader SessionOutputReader = new SessionOutputReader(session);
            // Request a pseudo terminal, if you do not you may not see the prompt
            session.requestPseudoTerminal("xterm",80,48, 0 , 0, "");

            //System.out.println("terminal : "+session.requestPseudoTerminal("gogrid",80,48, 0 , 0, ""));
            // Start the users shell
            //session.startShell();

            System.out.println(session.startShell());

            // ChannelInputStream in = session.getInputStream();
            // System.out.println(in);

            final ChannelOutputStream out = session.getOutputStream();
            out.write("ls\n".getBytes());
            //out.write("".getBytes());
            Thread.currentThread().sleep(1000*2);
            final SessionOutputReader sor = new SessionOutputReader(session);
            System.out.println("Hi there :"+sor.getOutput());
            
            //printf "$username\n$environment\n$password\n $cmdLineArgs" | $cer_exe/scpview $hostname  
//          #Pushes standard input to the scp command  
//          03.username=$(cat ~/bin/ml_username.txt)  
//          04.password=$(cat ~/bin/ml_password.txt)  
//          05.hostname=$(cat ~/bin/ml_hostname.txt)  
//          06.cmdLineArgs=$@ #printf swallows the 2nd * command line variables if use $@ directly in printf  
//          07.printf "$username\n$environment\n$password\n $cmdLineArgs" | $cer_exe/scpview $hostname

            //out.write("TERM=xterm screen\n".getBytes());
            out.write("echo \"SD049814\nSURROUND\nGO\n dir\" | /cerner/w_standard/surround/aixrs6000/scpview fork\n".getBytes());
            Thread.currentThread().sleep(1000*5);
            System.out.println("Hi there :"+sor.getOutput());



        } catch (final Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Login to " + hostname + " " + username + "/" + password + " failed");
        }

    }

    public static void main(final String[] args) {
        final test test = new test();
        test.connect();
    }

}
