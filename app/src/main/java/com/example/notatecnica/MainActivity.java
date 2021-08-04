package com.example.notatecnica;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText matricula, nombres, apellidos, edad, idCarrera;

    /**
     * Se sobreescribe el método onCreate(Bundle savedInstanceState), que permite crear objetos que contengan la información
     * de los estudiantes que se registren. La información que se almacene, será ingresada desde la aplicación móvil.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matricula=(EditText)findViewById(R.id.editTextNumMat);
        nombres=(EditText)findViewById(R.id.editTextNombres);
        apellidos=(EditText)findViewById(R.id.editTextApellidos);
        edad=(EditText)findViewById(R.id.editTextEdad);
        idCarrera=(EditText)findViewById(R.id.editTextIdCarrera);
    }

    /**
     * Este método se ejecutará al momento de hacer clic en el botón "Registrar" desde la aplicación móvil.
     * Permite guardar la información ingresada, la cual pertenece a un estudiante. La información es almacenada en un base datos local.
     * Si se completa exitosamente el registro, se limpiarán los cuadros de textos que llenó el usuario previamente.
     * @param view
     */
    public void registro(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion",null,1);
        SQLiteDatabase bd= admin.getWritableDatabase();

        String matriculaText= matricula.getText().toString();
        String nombresText= nombres.getText().toString();
        String apellidosText= apellidos.getText().toString();
        String edadText= edad.getText().toString();
        String idCarreraText= idCarrera.getText().toString();

        if(!matriculaText.isEmpty()||!nombresText.isEmpty()||!apellidosText.isEmpty()||!edadText.isEmpty()||!idCarreraText.isEmpty()){
            bd.execSQL("insert into estudiantes (id_estudiante, nombres,apellidos,edad,id_carrera)"+"values ("+matriculaText+",'"+nombresText+"','"+apellidosText+"',"+edadText+",'"+idCarreraText+"')");
            bd.close();
            matricula.setText("");
            nombres.setText("");
            apellidos.setText("");
            edad.setText("");
            idCarrera.setText("");
            Toast.makeText(this,"Se cargaron los datos del estudiantes", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Por favor, llene todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Este método se ejecutará al momento de hacer clic en el botón "Consultar" desde la aplicación móvil.
     * Permite consultar la información de un estudiante que ha sido registrado previamente.
     * Para realizar una consulta se deberá ingresar la matrícula del estudiante que se desea inspeccionar,
     * por lo que la búsqueda en la base de datos se realizará usando como identificador a dicha matrícula. Si la consulta
     * es exitosa, se mostrarán los datos del estudiante consultado en los cuadros de texto.
     * @param view
     */
    public void consultaMatricula(View view){
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper( this,  "administracion",  null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();

        String matriculaText= matricula.getText().toString();

        if(!matriculaText.isEmpty()){
            Cursor fila=bd.rawQuery(
                    "select nombres,apellidos,edad,id_carrera from estudiantes where "+"id_estudiante="+matriculaText,null);
            if(fila.moveToFirst()){
                nombres.setText(fila.getString(0));
                apellidos.setText(fila.getString(1));
                edad.setText(fila.getString(2));
                idCarrera.setText(fila.getString(3));
                Toast.makeText( this, "Consulta exitosa,", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText( this, "No existe un estudiante con dicha matricula", Toast.LENGTH_SHORT).show();
            }
            bd.close();
        }else{
            Toast.makeText(this, "Ingrese un numero de matricula.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Este método se ejecutará al momento de hacer clic en el botón "Modificar" desde la aplicación móvil.
     * Permite modificar la información de un estudiante registrado previamente. Para realizar una modificación, será necesario
     * llenar todos los campos. No es posible modificar la matrícula, ya que es el identificador (PK).
     * @param V
     */
    public void modificarInformacion(View V){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String matriculaText = matricula.getText().toString();
        String nombresText = nombres.getText().toString();
        String apellidosText = apellidos.getText().toString();
        String edadText = edad.getText().toString();
        String idCarreraText = idCarrera.getText().toString();

        if(!matriculaText.isEmpty()){
            if(nombresText.isEmpty()||apellidosText.isEmpty()||edadText.isEmpty()||idCarreraText.isEmpty()){
                Toast.makeText(this, "Ingrese todos los datos.", Toast.LENGTH_SHORT).show();
            }
            bd.execSQL("update estudiantes set id_estudiante="+matriculaText+",nombres='"+nombresText+"',apellidos='"+apellidosText+"',edad="+edadText+",id_carrera='"+idCarreraText+"' where id_estudiante="+matriculaText);
            matricula.setText("");
            nombres.setText("");
            apellidos.setText("");
            edad.setText("");
            idCarrera.setText("");
            bd.close();
            Toast.makeText(this, "Se modificaron los datos.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Ingrese una matrícula.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Este método se ejecutará al momento de hacer clic en el botón "Eliminar" desde la aplicación móvil.
     * Permite eliminar un estudiante utilizando como parámetro la matrícula. Si la matrícula existe
     * la eliminará, caso contrario, mostrará un mensaje en el que se menciona que la matrícula no ha sido registrada previamente.
     * @param v
     */
    public void eliminarEstudiante(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String matriculaText = matricula.getText().toString();

        if (!matriculaText.isEmpty()) {
            bd.execSQL("delete from estudiantes where id_estudiante=" + matriculaText);
            bd.close();
            matricula.setText("");
            nombres.setText("");
            apellidos.setText("");
            edad.setText("");
            idCarrera.setText("");
            Toast.makeText(this, "Estudiante eliminado de la base de datos.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ingrese un número de matrícula.", Toast.LENGTH_SHORT).show();
        }
    }
}