<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Productos;
use Illuminate\Support\Facades\Validator;

class productosController extends Controller
{
    public function listar(Request $request)
    {
        $perPage = 10; // Cantidad de items por página
        $page = $request->input('page', 1); // Obtener el número de página (default 1)

        $productos = Productos::paginate($perPage, ['*'], 'page', $page);

        $data = [
            'status' => '200',
            'message' => 'Lista de productos paginada',
            'productos' => $productos->items(), // Solo los items de la página actual
            'pagination' => [
                'total' => $productos->total(),
                'per_page' => $productos->perPage(),
                'current_page' => $productos->currentPage(),
                'last_page' => $productos->lastPage(),
                'from' => $productos->firstItem(),
                'to' => $productos->lastItem()
            ]
        ];

        return response()->json($data, 200);
    }

    public function crear(Request $request)
    {

        \Log::debug('Solicitud recibida', $request->all()); // ← Nuevo log

        $validator = Validator::make($request->all(), [
            'nombre' => 'required',
            'descripcion' => 'required',
            'precio_unit' => 'required',
        ]);

        if ($validator->fails()) {
            \Log::error('Error de validación', $validator->errors()->toArray()); // ← Nuevo log
            $data = [
                'status' => '400',
                'message' => 'Error de validacion',
                'errors' => $validator->errors()
            ];
            return response()->json($data, 400);
        }
        $producto = Productos::create([
            'nombre' => $request->nombre,
            'descripcion' => $request->descripcion,
            'precio_unit' => $request->precio_unit,
        ]);
        if (!$producto) {
            $data = [
                'status' => '500',
                'message' => 'Error al crear el producto'
            ];
            return response()->json($data, 500);
        }
        $data = [
            'status' => '201',
            'message' => 'Producto creado con exito',
            'producto' => $producto
        ];
        return response()->json($data, 201);
    }
    public function mostrar($id_producto)
    {
        $producto = Productos::find($id_producto);
        if (!$producto) {
            $data = [
                'status' => '404',
                'message' => 'Producto no encontrado'
            ];
            return response()->json($data, 404);
        }
        $data = [
            'status' => '200',
            'message' => 'Producto encontrado',
            'producto' => $producto
        ];

        return response()->json($producto, 200);
    }



    public function actualizar(Request $request, $id_producto)
    {
        $productos = Productos::find($id_producto);

        if (!$productos) {
            $data = [
                'status' => '404',
                'message' => 'Producto no encontrado'
            ];
            return response()->json($data, 404);
        }
        $validator = Validator::make($request->all(), [
            'nombre' => 'required',
            'descripcion' => 'required',
            'precio_unit' => 'required',
        ]);

        if ($validator->fails()) {
            $data = [
                'status' => '400',
                'message' => 'Error de validacion',
                'errors' => $validator->errors()
            ];
            return response()->json($data, 400);
        }

        $productos->name = $request->nombre;
        $productos->descripcion = $request->descripcion;
        $productos->precio_unit = $request->precio_unit;
        $productos->save();

        $data = [
            'status' => '200',
            'message' => 'Producto actualizado con exito',
            'producto' => $productos
        ];
        return response()->json($data, 200);
    }



    public function borrar($id_producto)
    {
        $producto = Productos::find($id_producto);
        if (!$producto) {
            $data = [
                'status' => '404',
                'message' => 'Producto no encontrado'
            ];
            return response()->json($data, 404);
        }
        $producto->delete();

        $data = [
            'status' => '200',
            'message' => 'Producto eliminado con exito'
        ];
        return response()->json($data, 200);
    }
}