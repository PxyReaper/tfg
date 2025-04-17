<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Productos extends Model
{
    use HasFactory;

    protected $primaryKey = 'id_producto'; // Especifica la clave primaria
    public $timestamps = false; // ← Desactiva los timestamps
    protected $table = 'productos';

    protected $fillable = [
        'nombre',
        'descripcion',
        'precio_unit',
    ];

}
