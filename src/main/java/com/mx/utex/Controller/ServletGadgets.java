package com.mx.utex.Controller;

import com.mx.utex.Model.Gadgets.BeanGadgets;
import com.mx.utex.Model.Address.BeanAddress;
import com.mx.utex.Model.Gadgets.DaoGadgets;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletGadgets", value = "/ServletGadgets")
public class ServletGadgets extends HttpServlet {
    private Map map = new HashMap();
    BeanGadgets beanGadgets = new BeanGadgets();
    BeanAddress beanAddress = new BeanAddress();
    DaoGadgets daoGadgets = new DaoGadgets();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        switch (action) {
            case "Listar":
                List<BeanGadgets> listGadgets = new DaoGadgets().Listar();
                map.put("listGadgets", listGadgets);
                write(response, map);
                map.clear();
                return;
            case "ListarId":
                int id = Integer.parseInt(request.getParameter("id"));
                map.put("Producto", new DaoGadgets().ListarId(id));
                write(response,map);
                map.clear();
                return;
            case "Registrar":
                String nombre = request.getParameter("nombre");
                String fecharegistro = request.getParameter("fecharegistro");
                String calle = request.getParameter("calle");
                String colonia = request.getParameter("colonia");
                int codigo_postal = Integer.parseInt(request.getParameter("codigo_postal"));
                String estado = request.getParameter("estado");
                String pais = request.getParameter("pais");

                beanGadgets.setNombre(nombre);
                beanGadgets.setFecharegistro(fecharegistro);

                beanAddress.setCalle(calle);
                beanAddress.setColonia(colonia);
                beanAddress.setcodigo_postal(codigo_postal);
                beanAddress.setEstado(estado);
                beanAddress.setPais(pais);

                beanGadgets.setdireccion_fabricante(beanAddress);

                daoGadgets.agregar(beanGadgets);
                break;
            case "Modificar":
                int id1 = Integer.parseInt(request.getParameter("idproducto"));
                String nombre1 =  request.getParameter("nombre");
                String fecharegistro1 = request.getParameter("fecharegistro");
                String calle1 = request.getParameter("calle");
                String colonia1 = request.getParameter("colonia");
                int codigo_postal1 = Integer.parseInt(request.getParameter("codigo_postal"));
                String estado1 = request.getParameter("estado");
                String pais1 = request.getParameter("pais");

                beanGadgets.setId(id1);
                beanGadgets.setNombre(nombre1);
                beanGadgets.setFecharegistro(fecharegistro1);

                beanAddress.setCalle(calle1);
                beanAddress.setColonia(colonia1);
                beanAddress.setcodigo_postal(codigo_postal1);
                beanAddress.setEstado(estado1);
                beanAddress.setPais(pais1);

                beanGadgets.setdireccion_fabricante(beanAddress);

                daoGadgets.modificar(beanGadgets);
                request.getRequestDispatcher("/views/index.jsp").forward(request, response);
                break;
            case "Eliminar":
                int id2 = Integer.parseInt(request.getParameter("id"));
                daoGadgets = new DaoGadgets();
                daoGadgets.eliminar(id2);
                request.getRequestDispatcher("/views/index.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            processRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            processRequest(request,response);
        }


    private void write(HttpServletResponse response, Map<String, Object> map) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(map));
    }
}
