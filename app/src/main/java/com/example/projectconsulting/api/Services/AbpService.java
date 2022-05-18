package com.example.projectconsulting.api.Services;


import com.example.projectconsulting.models.Botchats;
import com.example.projectconsulting.models.Cursos;
import com.example.projectconsulting.models.Dias;
import com.example.projectconsulting.models.Grup;
import com.example.projectconsulting.models.Grups_has_horaris;
import com.example.projectconsulting.models.Grups_has_llistes_skills;
import com.example.projectconsulting.models.Horaris;
import com.example.projectconsulting.models.Imatge_usuari;
import com.example.projectconsulting.models.Kpis;
import com.example.projectconsulting.models.Llistes_skills;
import com.example.projectconsulting.models.Skills;
import com.example.projectconsulting.models.Usuaris;
import com.example.projectconsulting.models.Valoracions;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AbpService {
    @GET("2022/dam02/api/usuaris/correu/{correu}")
    Call<List<Usuaris>> getNom(@Path("correu") String correu);

    @GET("2022/dam02/api/grups/{id}")
    Call <List<Grup>> getGrups(@Path("grups_id") float id);

    @GET("2022/dam02/api/grups")
    Call <List<Grup>> getGrup();

    @GET("2022/dam02/api/cursos/{usuari_id}")
    Call <List<Cursos>> getCursos(@Path("usuari_id") float id);

    @GET("2022/dam02/api/usuaris")
    Call<List<Usuaris>> getUsers();

    @GET("2022/dam02/api/kpis")
    Call<List<Kpis>> getKpis();

    @GET("2022/dam02/api/skills")
    Call<List<Skills>> getSkills();

    @GET("2022/dam02/api/llistes_skills/")
    Call<List<Llistes_skills>> getLlistesSkills();

    @GET("2022/dam02/api/kpis/id/{id}")
    Call<List<Kpis>> getKpiPorIdSkill(@Path("id")int id);

    @GET("2022/dam02/api/llistes_skills/{nom}")
    Call<List<Llistes_skills>> getNomLlistaSkill(@Path("nom")String nom);

    @GET("2022/dam02/api/skills/buscarPorLlistaSkill/{id}")
    Call<List<Skills>> getSkillsPorIdListaSkill(@Path("id")int id);

    @GET("2022/dam02/api/cursos")
    Call<List<Cursos>> getAllCursos();

    @POST("2022/dam02/api/valoracions")
    Call<Valoracions> Postvaloracions(@Body Valoracions valoracion);

    @GET("2022/dam02/api/grups_has_llistes_skills/grups_id/{grups_id}")
    Call<List<Grups_has_llistes_skills>> getGrupHasLlistesSkills(@Path("grups_id") int grups_id);

    @GET("2022/dam02/api/llistes_skills/id/{id}")
    Call <List<Llistes_skills>> getLlistesSkillsPorId(@Path ("id") int id);

    @POST("2022/dam02/api/botchats")
    Call<Botchats> PostMessage(@Body Botchats botchats);

    @PUT("2022/dam02/api/valoracions/{kpis_id}")
    Call<Valoracions> updateValoracion(@Path("kpis_id") int id, @Body Valoracions valoracions);

    @POST("2022/dam02/api/usuaris/update/{id}")
    Call<Usuaris> updateUsuari(@Path("id") int id, @Body Usuaris _usuari);

    @GET("2022/dam02/api/imatge_usuari/{id}")
    Call<Imatge_usuari> getImatge(@Path("id") float id);

    @GET("2022/dam02/api/horaris/")
    Call <List<Horaris>> getHoraris();

    @GET("2022/dam02/api/grups_has_horaris/")
    Call <List<Grups_has_horaris>> getGrupsHasHoraris();

    @GET("2022/dam02/api/dias/")
    Call <List<Dias>> getDias();

    @GET("2022/dam02/api/valoracions")
    Call <List<Valoracions>> getValoracions();

}