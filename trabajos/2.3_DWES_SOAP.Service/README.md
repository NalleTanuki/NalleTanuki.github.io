# SOAP Web Service: Insert User with Complex Structure

This project demonstrates how to create and consume a SOAP web service in PHP using the NuSOAP library (v0.9.11, installed via Composer). The service allows inserting a user by sending a structured data object.

## 📦 Project Structure
SOAP_Service/
├── config/
│ └── conexion.php # Database connection
├── models/
│ └── Usuario.php # User model with the insert_usuario() method
├── InsertCategoria.php # SOAP server (WSDL provider + logic)
├── formconsumir.php # HTML form + SOAP client using cURL
├── vendor/ # Composer dependencies (NuSOAP)
└── composer.json

## 🔧 Requirements

- PHP 7.4 or higher
- Composer
- Web server (Apache/Nginx)
- Localhost or a server with `InsertCategoria.php` accessible via HTTP

## 📥 Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-user/your-repo.git
   cd your-repo/SOAP_Service
2. **Install NuSOAP via Composer:**
   ```bash
   composer require econea/nusoap
3. **Configure database connection in config/conexion.php (Set your DB credentials: host, database name, user, password)**
4. **Ensure your web server has access to the project and InsertCategoria.php is reachable**

# 🚀 How It Works
# 🧩 InsertCategoria.php
This file defines the SOAP server. Key features:
  - Registers a complex input type InsertCategoria with three fields: usu_nom, usu_ape, usu_correo.
  - Defines a response type response with a Resultado boolean field.
  - Implements the function InsertCategoriaService($request) which:
      - Includes the DB connection and user model.
      - Calls $usuario->insert_usuario(...) to store the user in the DB.
      - Returns a SOAP-compliant response.

# 📤 formconsumir.php
This is the SOAP client and form interface:
  -  Displays an HTML form to collect user data.
  -  Uses cURL to send a manually constructed SOAP XML request.
  -  Receives and displays the response from the SOAP server.

# 🧪 Example Usage
1. **Open formconsumir.php in your browser:**
````bash
http://localhost/dwes/SOAP_Service/formconsumir.php
`````
2. **Fill out the form and submit.**
3. **You will see a response similar to:**
````xml
<Resultado>true</Resultado>
````
# ✅ Response
The response includes:
````xml
<Result>
    <Resultado>true</Resultado>
</Result>
````
Indicating that the user was successfully inserted.

# 🛠️ Notes
The response is always *true* for now; in a real implementation, error handling and validation would be required.

Make sure your web server allows *.php* execution and the endpoint is accessible via HTTP.

If you get a *500* error or no response, check file paths and SOAP endpoint URL.

# 📚 Technologies Used
  - PHP
  - NuSOAP (via Composer)
  - HTML + cURL
  - MySQL (for real insertions)

# 📄 License
This project is for educational purposes only. Created by AV.
