<?php
/**
 * Clase Usuario
 * 
 * Extiende de la clase "Conectar" para acceder a la BD y permite insertar nuevos usuarios en la tabla tm_usuario
 */
    class Usuario extends Conectar
    {
        /**Metodo publico para insertar un nuevo usuario en la BD
         * 
         * @param string $usu_nom -> Nombre del usuario
         * @param string $usu_ape -> Apellido del usuario
         * @param string $usu_correo -> Correo electronico del usuario
         * 
         * Este metodo prepara una consulta SQL con parametros para evitar inyecciones SQL
         */
        public function insert_usuario($usu_nom, $usu_ape, $usu_correo)
        {
            $conectar = parent::conexion(); // Obtiene conexion a la BD desde la clase padre
            parent::set_names(); // Establece la codificacion UTF-8 para evitar errores con caracteres especiales y acentos

            /**
             * Consulta SQL para insertar un nuevo registro en la tabla tm_usuario
             * 
             * El campo 'usu_id' se deja como NULL porque es autoincremental
             * El campo 'est' se establece a '1' para indicar que el usuario esta activo
             */
            $sql = "INSERT INTO tm_usuario (usu_id, usu_nom, usu_ape, usu_correo, est) VALUES (NULL, ?, ?, ?, '1');";
            
            $stmt = $conectar -> prepare($sql); // Prepara la consulta para evitar inyecciones SQL
            
            // Asociar los valroes a los parametros del SQL (posiciones 1, 2 y 3)
            $stmt -> bindValue(1, $usu_nom);
            $stmt -> bindValue(2, $usu_ape);
            $stmt -> bindValue(3, $usu_correo);
            
            $stmt -> execute(); // Ejecutar la consulta
        }
    }
?>