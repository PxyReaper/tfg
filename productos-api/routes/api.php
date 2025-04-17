<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

use App\Http\Controllers\productosController;

Route::get('/productos', [productosController::class, 'listar']);

Route::get('/productos/{id_producto}', [productosController::class, 'mostrar']);

Route::post('/productos', [productosController::class, 'crear']);

Route::put('/productos/{id_producto}', [productosController::class, 'actualizar']);

Route::delete('/productos/{id_producto}', [productosController::class, 'borrar']);
