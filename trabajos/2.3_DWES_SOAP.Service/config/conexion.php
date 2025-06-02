<?php
/**
 * Clase Conectar
 * 
 * Establece la conexion a la BD utilizando PDO.
 * Tambien permite configurar el conjunto de caracteres, UTF-8.
 * 
 * Es usada por los modelos que necesiten acceder a la BD.
 */
    class Conectar
    {
        protected $dbh; // Creamos un variable protegida que almacenara la instancia PDO de la conexion

        /** Metodo protedigo que almacenara la conexion a la BD
         * 
         * Usa PDO para conectarse a MySQL.
         * Si la conexion falla, lanza una excepcion.
         * 
         * @return PDO Objeto de conexion activo
         */
        protected function conexion(){
            try
            {
                // Crea un nuevo objeto PDO con los parametros de conexion (host, nombre BD, usuario, contrasenha)
                $conectar = $this -> dbh = new PDO
                (
                    'mysql:host=localhost;dbname=ejercicio1_soap-wsdl', // DSN
                    'root', //Usuario de la BD
                    '' //Contrasenha (vacia por defecto en XAMPP)
                );
                return $conectar; // se devuelve el objeto de conexion creado
            }
            catch(Exception $e) //Si ocurre algun error lo captura
            {
                print "¡Error!: " . $e -> getMessage() . "<br/>"; //se muestra un mensaje con el error
                die(); //Detener la ejecucion, termina el script
            }
        }

        /** Metodo publico para establecer el conjunto de caracteres UTF-8
         * 
         * Esto es importante para evitar problemas con acentos y caracteres especiales
        */
        public function set_names()
        {
            // Ejecuta una consulta SQL para establecer el conjunto de caracteres UTF-8 en la conexion actual
            return $this -> dbh -> query("SET NAMES 'utf8'");
        }
    }
?>