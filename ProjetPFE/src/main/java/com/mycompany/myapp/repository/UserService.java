package com.mycompany.myapp.repository;
import com.mycompany.myapp.service.EmailAlreadyUsedException;
public class UserService {
    
    public void registerUser(String email) {
        // Vérification si l'e-mail est déjà utilisé
        if (emailAlreadyUsed(email)) {
            throw new EmailAlreadyUsedException();
        }
        // Code pour enregistrer l'utilisateur
        // ...
    }

    private boolean emailAlreadyUsed(String email) {
        // Vérification si l'e-mail est déjà utilisé
        // ...
        return true; // Exemple pour démonstration, normalement il y aurait une vraie vérification
    }

    public static void main(String[] args) {
        UserService userService = new UserService();
        String email = "johndoe@example.com";

        try {
            userService.registerUser(email);
        } catch (EmailAlreadyUsedException e) {
            // Traitement de l'exception
            System.out.println("Impossible de créer le compte : " + e.getMessage());
        }
    }
}
