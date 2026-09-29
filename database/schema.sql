--
-- PostgreSQL database dump
--

\restrict SHTYllqaN5duJylxaOA9e3G7bvFC8PQd1zqFLmKA0F3bZdILnWDDEAkAyaaBCEt

-- Dumped from database version 18.6
-- Dumped by pg_dump version 18.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: schema_affairesociale; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA schema_affairesociale;


--
-- Name: schema_carriere; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA schema_carriere;


--
-- Name: schema_formation; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA schema_formation;


--
-- Name: schema_pta; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA schema_pta;


--
-- Name: schema_utilisateur; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA schema_utilisateur;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: besoin_en_personnel_statut; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.besoin_en_personnel_statut (
    statutbep_id bigint,
    besoin_en_personnel_id bigint NOT NULL
);


--
-- Name: formation_utilisateur; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.formation_utilisateur (
    formation_id bigint NOT NULL,
    utilisateur_id bigint NOT NULL
);


--
-- Name: param_bureau; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.param_bureau (
    param_id bigint NOT NULL,
    bureau_id bigint NOT NULL
);


--
-- Name: param_division; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.param_division (
    param_id bigint NOT NULL,
    division_id bigint NOT NULL
);


--
-- Name: participant_utilisateur; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.participant_utilisateur (
    participant_id bigint NOT NULL,
    utilisateur_id bigint NOT NULL
);


--
-- Name: seq_account; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_account
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_acte; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_acte
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_acton_pta; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_acton_pta
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_actu; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_actu
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_agent; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_agent
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_amendment; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_amendment
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_avancement; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_avancement
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_benbre_disc; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_benbre_disc
    START WITH 1
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_bep_f; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_bep_f
    START WITH 1
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_bep_f_d; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_bep_f_d
    START WITH 1
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_besoinenper; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_besoinenper
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_bord; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_bord
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_bureau; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_bureau
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_cat_actu; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_cat_actu
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_cfp; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_cfp
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_classe; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_classe
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_classeprofdiscipline; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_classeprofdiscipline
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_cont; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_cont
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_contract; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_contract
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_corps_grade; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_corps_grade
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_destinationsouhaitee; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_destinationsouhaitee
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_dip; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_dip
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_dip_aca; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_dip_aca
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_dip_ped; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_dip_ped
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_dip_prof; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_dip_prof
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_diplome; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_diplome
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_diplome_list; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_diplome_list
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_direction; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_direction
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_discipline; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_discipline
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_disciplinequantum; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_disciplinequantum
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_division; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_division
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_dossier_agent; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_dossier_agent
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_eef; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_eef
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_eef_spec; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_eef_spec
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_eefmi; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_eefmi
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_emergency_level; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_emergency_level
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_entity; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_entity
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_etablissement; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_etablissement
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_etat_civil; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_etat_civil
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_expressionbesoin; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_expressionbesoin
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_fichesynoptique; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_fichesynoptique
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_file; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_file
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_filiere; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_filiere
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_filierediscipline; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_filierediscipline
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_fonction; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_fonction
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_formation; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_formation
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_grade; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_grade
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_ia; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_ia
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_ief; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_ief
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_imp; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_imp
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_initial; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_initial
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_initial_pta_travail; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_initial_pta_travail
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_menu; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_menu
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_mode_calcul; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_mode_calcul
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_mutation; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_mutation
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_niveau; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_niveau
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_notification; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_notification
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_originedemandeurlog; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_originedemandeurlog
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_params_corgrade; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_params_corgrade
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_params_corgrade_pk; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_params_corgrade_pk
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_pec; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_pec
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_permu; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_permu
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_pj; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_pj
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_plan_travail; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_plan_travail
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_pmsm; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_pmsm
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_process; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_process
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_profdiscipline; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_profdiscipline
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_profile; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_profile
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_region; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_region
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_report_realisation; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_report_realisation
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_reseulta_pta; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_reseulta_pta
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_role; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_role
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_sequence_ref; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_sequence_ref
    START WITH 1
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_serie; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_serie
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_serieclasseprofdiscipline; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_serieclasseprofdiscipline
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_serieniveaudiscipline; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_serieniveaudiscipline
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_service; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_service
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_situation_administrative; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_situation_administrative
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_smfpa; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_smfpa
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_specialiteeef; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_specialiteeef
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_speciality; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_speciality
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_speciality_etablissement; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_speciality_etablissement
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_status_permu; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_status_permu
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_statut_acte; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_statut_acte
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_statut_pec; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_statut_pec
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_statutbep; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_statutbep
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_statutmut; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_statutmut
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_statuttraitement_acte; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_statuttraitement_acte
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_str; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_str
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_sub_acton_pta; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_sub_acton_pta
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_td_sequence; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_td_sequence
    START WITH 1
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_trait_perm; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_trait_perm
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_traitement_acte; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_traitement_acte
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_traitement_pec; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_traitement_pec
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_traitementmutation; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_traitementmutation
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_type_acte; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_type_acte
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_type_art; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_type_art
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_type_dip; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_type_dip
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_type_mat; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_type_mat
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_type_poste; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_type_poste
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_type_systeme_enseignement; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_type_systeme_enseignement
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_typeaa; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_typeaa
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_typeag; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_typeag
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_typepec; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_typepec
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_user; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_user
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_user_archived; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.seq_user_archived
    START WITH 100
    INCREMENT BY 2
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_account; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_account (
    user_id bigint NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    deleted boolean NOT NULL,
    enabled boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp(6) without time zone,
    failed_attempt integer,
    first_attempt boolean,
    lock_time timestamp(6) without time zone,
    login character varying(255) NOT NULL,
    password character varying(255)
);


--
-- Name: td_acte_piecejointes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_acte_piecejointes (
    acte_acte_id bigint NOT NULL,
    piecejointes_id bigint NOT NULL
);


--
-- Name: td_acte_responsabletraitements; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_acte_responsabletraitements (
    acte_acte_id bigint NOT NULL,
    responsabletraitements_id bigint CONSTRAINT td_acte_responsabletraitemen_responsabletraitements_id_not_null NOT NULL
);


--
-- Name: td_acte_traitementactes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_acte_traitementactes (
    acte_acte_id bigint NOT NULL,
    traitementactes_traitement_id bigint NOT NULL
);


--
-- Name: td_amendment; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_amendment (
    id bigint NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    deleted boolean NOT NULL,
    enabled boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp(6) without time zone,
    amendment_object text,
    contract_id bigint,
    parapher_id bigint
);


--
-- Name: td_attestationstage_piecesjoint; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_attestationstage_piecesjoint (
    attestationstage_attestation_id bigint CONSTRAINT td_attestationstage_piecesj_attestationstage_attestati_not_null NOT NULL,
    piecesjoint_id bigint NOT NULL
);


--
-- Name: td_attestationstage_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_attestationstage_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_avisdemandestage_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_avisdemandestage_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_besoinenpersonnel_bepfilieredisciplines; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_besoinenpersonnel_bepfilieredisciplines (
    besoinenpersonnel_id bigint CONSTRAINT td_besoinenpersonnel_bepfilieredi_besoinenpersonnel_id_not_null NOT NULL,
    bepfilieredisciplines_id bigint CONSTRAINT td_besoinenpersonnel_bepfilie_bepfilieredisciplines_id_not_null NOT NULL
);


--
-- Name: td_besoinenpersonnel_besoinenpersonnelfilieres; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_besoinenpersonnel_besoinenpersonnelfilieres (
    besoinenpersonnel_id bigint CONSTRAINT td_besoinenpersonnel_besoinenpers_besoinenpersonnel_id_not_null NOT NULL,
    besoinenpersonnelfilieres_id bigint CONSTRAINT td_besoinenpersonnel_besoin_besoinenpersonnelfilieres__not_null NOT NULL
);


--
-- Name: td_campagne_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_campagne_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_contract; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_contract (
    id bigint NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    deleted boolean NOT NULL,
    enabled boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp(6) without time zone,
    contract_number character varying(50),
    emergency_level character varying(255),
    contract_object character varying(150),
    file_id bigint,
    user_id bigint,
    entity_id bigint,
    process_id bigint,
    emergency_level_id bigint,
    CONSTRAINT td_contract_emergency_level_check CHECK (((emergency_level)::text = ANY (ARRAY[('HIGH_URGENCY'::character varying)::text, ('URGENCY'::character varying)::text, ('NORMAL'::character varying)::text])))
);


--
-- Name: td_courrier_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_courrier_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_demandestage_justificatfs; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_demandestage_justificatfs (
    demandestage_demande_id bigint NOT NULL,
    justificatfs_id bigint NOT NULL
);


--
-- Name: td_demandestage_justificatfsauthorisationstage; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_demandestage_justificatfsauthorisationstage (
    demandestage_demande_id bigint CONSTRAINT td_demandestage_justificatfsau_demandestage_demande_id_not_null NOT NULL,
    justificatfsauthorisationstage_id bigint CONSTRAINT td_demandestage_justificatf_justificatfsauthorisations_not_null NOT NULL
);


--
-- Name: td_demandestage_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_demandestage_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_disciplinestage; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_disciplinestage (
    discipline_id bigint NOT NULL,
    discipline_code character varying(20),
    discipline_deleted boolean,
    discipline_libelle character varying(100)
);


--
-- Name: td_disciplinestage_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_disciplinestage_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_disposableemail; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_disposableemail (
    dis_id bigint NOT NULL,
    dis_domain character varying(255)
);


--
-- Name: td_disposableemail_dis_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_disposableemail_dis_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_disposableemail_dis_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.td_disposableemail_dis_id_seq OWNED BY public.td_disposableemail.dis_id;


--
-- Name: td_dossieragent_actes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_dossieragent_actes (
    dossieragent_dos_id bigint NOT NULL,
    actes_acte_id bigint NOT NULL
);


--
-- Name: td_dossieragent_avancements; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_dossieragent_avancements (
    dossieragent_dos_id bigint NOT NULL,
    avancements_avan_id bigint NOT NULL
);


--
-- Name: td_dossieragent_diplomes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_dossieragent_diplomes (
    dossieragent_dos_id bigint NOT NULL,
    diplomes_dip_id bigint NOT NULL
);


--
-- Name: td_dossieragent_etatcivil; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_dossieragent_etatcivil (
    dossieragent_dos_id bigint NOT NULL,
    etatcivil_etat_id bigint NOT NULL
);


--
-- Name: td_entite; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_entite (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(50)
);


--
-- Name: td_expressionbesoin; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_expressionbesoin (
    id bigint NOT NULL,
    campagne_id bigint,
    expbesoin_id bigint NOT NULL,
    campagne_camp_id bigint
);


--
-- Name: td_expressionbesoin_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_expressionbesoin_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_expressiondebesoin_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_expressiondebesoin_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_failed_mail; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_failed_mail (
    fai_id bigint NOT NULL,
    created_date timestamp(6) without time zone,
    fai_email character varying(255),
    fai_is_sent boolean DEFAULT false,
    fai_subject character varying(255),
    fai_text text
);


--
-- Name: td_failed_mail_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_failed_mail_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_failedmail; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_failedmail (
    fai_id bigint NOT NULL,
    fai_createddate timestamp(6) without time zone,
    fai_email character varying(255),
    fai_issent boolean DEFAULT false,
    fai_subject character varying(255),
    fai_text text
);


--
-- Name: td_failedmail_fai_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_failedmail_fai_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_failedmail_fai_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.td_failedmail_fai_id_seq OWNED BY public.td_failedmail.fai_id;


--
-- Name: td_fichesynoptique_classeprofdisciplines; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_fichesynoptique_classeprofdisciplines (
    fichesynoptique_id bigint CONSTRAINT td_fichesynoptique_classeprofdiscip_fichesynoptique_id_not_null NOT NULL,
    classeprofdisciplines_id bigint CONSTRAINT td_fichesynoptique_classeprof_classeprofdisciplines_id_not_null NOT NULL
);


--
-- Name: td_fichesynoptique_filieredisciplines; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_fichesynoptique_filieredisciplines (
    fichesynoptique_id bigint CONSTRAINT td_fichesynoptique_filieredisciplin_fichesynoptique_id_not_null NOT NULL,
    filieredisciplines_id bigint CONSTRAINT td_fichesynoptique_filierediscip_filieredisciplines_id_not_null NOT NULL
);


--
-- Name: td_fichesynoptique_serieclasseprofdisciplines; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_fichesynoptique_serieclasseprofdisciplines (
    fichesynoptique_id bigint CONSTRAINT td_fichesynoptique_serieclasseprofd_fichesynoptique_id_not_null NOT NULL,
    serieclasseprofdisciplines_id bigint CONSTRAINT td_fichesynoptique_seriecla_serieclasseprofdisciplines_not_null NOT NULL
);


--
-- Name: td_fichesynoptique_serieniveaudisciplines; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_fichesynoptique_serieniveaudisciplines (
    fichesynoptique_id bigint CONSTRAINT td_fichesynoptique_serieniveaudisci_fichesynoptique_id_not_null NOT NULL,
    serieniveaudisciplines_id bigint CONSTRAINT td_fichesynoptique_serienive_serieniveaudisciplines_id_not_null NOT NULL
);


--
-- Name: td_fichiercanditure_files; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_fichiercanditure_files (
    fichiercandidature_id bigint NOT NULL,
    files_id bigint NOT NULL
);


--
-- Name: td_file; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_file (
    id bigint NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    deleted boolean NOT NULL,
    enabled boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp(6) without time zone,
    download_url character varying(255),
    file_code character varying(255),
    file_size bigint,
    file_type character varying(255),
    generated_name character varying(255),
    original_name character varying(255)
);


--
-- Name: td_offretechniquefinanciere_files; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_offretechniquefinanciere_files (
    offretechniquefinanciere_id bigint CONSTRAINT td_offretechniquefinanciere_offretechniquefinanciere_i_not_null NOT NULL,
    files_id bigint NOT NULL
);


--
-- Name: td_parametre_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_parametre_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_parapher; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_parapher (
    id bigint NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    deleted boolean NOT NULL,
    enabled boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp(6) without time zone,
    user_id bigint
);


--
-- Name: td_piecejoint_files; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_piecejoint_files (
    piecejointes_pj_id bigint NOT NULL,
    files_id bigint NOT NULL
);


--
-- Name: td_planformation_files; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_planformation_files (
    planformation_id bigint NOT NULL,
    files_id bigint NOT NULL
);


--
-- Name: td_planningformation_files; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_planningformation_files (
    planningformation_id bigint NOT NULL,
    files_id bigint NOT NULL
);


--
-- Name: td_priseencharge_traitementpriseencharges; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_priseencharge_traitementpriseencharges (
    priseencharge_pec_id bigint CONSTRAINT td_priseencharge_traitementprisee_priseencharge_pec_id_not_null NOT NULL,
    traitementpriseencharges_traitement_id bigint CONSTRAINT td_priseencharge_traitement_traitementpriseencharges_t_not_null NOT NULL
);


--
-- Name: td_rapport_files; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_rapport_files (
    rapport_id bigint NOT NULL,
    files_id bigint NOT NULL
);


--
-- Name: td_rapport_pv; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_rapport_pv (
    rapport_id bigint NOT NULL,
    pv_id bigint NOT NULL
);


--
-- Name: td_rapportstage_piecesjoint; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_rapportstage_piecesjoint (
    rapportstage_rapport_id bigint NOT NULL,
    piecesjoint_id bigint NOT NULL
);


--
-- Name: td_rapportstage_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_rapportstage_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_role (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(50)
);


--
-- Name: td_traitementcampagne_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_traitementcampagne_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_traitementcourrier_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_traitementcourrier_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_traitementdemandestage_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_traitementdemandestage_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_traitementexpressiondebesoin_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_traitementexpressiondebesoin_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_traitementparametre_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_traitementparametre_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_user (
    id bigint NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    deleted boolean NOT NULL,
    enabled boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp(6) without time zone,
    email character varying(150),
    first_name character varying(100),
    last_name character varying(50),
    phone_number character varying(50),
    using_mfa boolean,
    file_id bigint,
    paraphe_id bigint
);


--
-- Name: td_utilisateur; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.td_utilisateur (
    uti_id bigint NOT NULL,
    uti_createdby bigint,
    uti_createddate timestamp(6) without time zone,
    uti_modifiedby bigint,
    uti_modifieddate timestamp(6) without time zone,
    uti_adresse character varying(255),
    uti_email character varying(30),
    uti_firstlog boolean DEFAULT true,
    uti_isdeleted boolean,
    uti_nom character varying(50),
    uti_password character varying(255),
    uti_prenom character varying(255) NOT NULL,
    uti_status boolean DEFAULT true,
    uti_telephone character varying(50),
    uti_pro_id bigint NOT NULL
);


--
-- Name: td_utilisateur_uti_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.td_utilisateur_uti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_utilisateur_uti_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.td_utilisateur_uti_id_seq OWNED BY public.td_utilisateur.uti_id;


--
-- Name: token; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.token (
    id bigint NOT NULL,
    expired boolean NOT NULL,
    revoked boolean NOT NULL,
    token text,
    token_type character varying(255),
    account_id bigint,
    CONSTRAINT token_token_type_check CHECK (((token_type)::text = 'BEARER'::text))
);


--
-- Name: token_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.token_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tp_emergency_level; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tp_emergency_level (
    id bigint NOT NULL,
    code character varying(50),
    description character varying(255),
    duration integer
);


--
-- Name: tp_menu; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tp_menu (
    men_id bigint NOT NULL,
    men_icontype character varying(255),
    men_path character varying(255),
    men_title character varying(255),
    men_type character varying(255)
);


--
-- Name: tp_menu_children; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tp_menu_children (
    menu_menu_id bigint NOT NULL,
    children_menu_id bigint NOT NULL
);


--
-- Name: tp_menu_men_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tp_menu_men_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tp_menu_men_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.tp_menu_men_id_seq OWNED BY public.tp_menu.men_id;


--
-- Name: tp_niveauscolaire; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tp_niveauscolaire (
    niveau_id bigint NOT NULL,
    niveau_code character varying(20),
    niveau_deleted boolean,
    niveau_libelle character varying(100)
);


--
-- Name: tp_niveauscolaire_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tp_niveauscolaire_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tp_processing; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tp_processing (
    id bigint NOT NULL,
    process_code character varying(5),
    process_label character varying(20)
);


--
-- Name: tp_profil; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tp_profil (
    pro_id bigint NOT NULL,
    pro_code character varying(10),
    pro_libelle character varying(100)
);


--
-- Name: tp_profil_pro_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tp_profil_pro_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tp_profil_pro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.tp_profil_pro_id_seq OWNED BY public.tp_profil.pro_id;


--
-- Name: tp_profile_menus; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tp_profile_menus (
    profile_pro_id bigint NOT NULL,
    menus_pmsm_id bigint NOT NULL
);


--
-- Name: tp_statutcampagne_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tp_statutcampagne_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tp_statutcourrier_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tp_statutcourrier_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tp_statutdemandestage; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tp_statutdemandestage (
    statudeman_id bigint NOT NULL,
    statudeman_code character varying(20),
    statudeman_deleted boolean,
    statudeman_libelle character varying(100)
);


--
-- Name: tp_statutdemandestage_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tp_statutdemandestage_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tp_statutexpressiondebesoin_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tp_statutexpressiondebesoin_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tp_statutparametre_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tp_statutparametre_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tp_typedemandecourrier; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tp_typedemandecourrier (
    typedemcou_id bigint NOT NULL,
    typedemcou_code character varying(20) NOT NULL,
    typedemcou_deleted boolean,
    typedemcou_libelle character varying(100) NOT NULL
);


--
-- Name: tp_typedemandecourrier_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tp_typedemandecourrier_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: tr_bepfilierediscipline_besoinennombredisciplines; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tr_bepfilierediscipline_besoinennombredisciplines (
    bepfilierediscipline_id bigint CONSTRAINT tr_bepfilierediscipline_besoin_bepfilierediscipline_id_not_null NOT NULL,
    besoinennombredisciplines_id bigint CONSTRAINT tr_bepfilierediscipline_bes_besoinennombredisciplines__not_null NOT NULL
);


--
-- Name: tr_classeprofdiscipline_profdiscipline; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tr_classeprofdiscipline_profdiscipline (
    classeprofdiscipline_id bigint CONSTRAINT tr_classeprofdiscipline_profdi_classeprofdiscipline_id_not_null NOT NULL,
    profdiscipline_id bigint CONSTRAINT tr_classeprofdiscipline_profdiscipli_profdiscipline_id_not_null NOT NULL
);


--
-- Name: tr_contract_file; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tr_contract_file (
    contract_id bigint NOT NULL,
    file_additional_id bigint NOT NULL
);


--
-- Name: tr_contract_parapher; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tr_contract_parapher (
    contract_id bigint NOT NULL,
    parapher_id bigint NOT NULL
);


--
-- Name: tr_filierediscipline_disciplinequantums; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tr_filierediscipline_disciplinequantums (
    filierediscipline_id bigint CONSTRAINT tr_filierediscipline_disciplinequ_filierediscipline_id_not_null NOT NULL,
    disciplinequantums_id bigint CONSTRAINT tr_filierediscipline_disciplineq_disciplinequantums_id_not_null NOT NULL
);


--
-- Name: tr_profdiscipline_disciplinequantums; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tr_profdiscipline_disciplinequantums (
    profdiscipline_id bigint NOT NULL,
    disciplinequantums_id bigint CONSTRAINT tr_profdiscipline_disciplinequan_disciplinequantums_id_not_null NOT NULL
);


--
-- Name: tr_profilmenu; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tr_profilmenu (
    pro_id bigint NOT NULL,
    men_id bigint NOT NULL
);


--
-- Name: tr_serieclasseprofdiscipline_profdiscipline; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tr_serieclasseprofdiscipline_profdiscipline (
    serieclasseprofdiscipline_id bigint CONSTRAINT tr_serieclasseprofdisciplin_serieclasseprofdiscipline__not_null NOT NULL,
    profdiscipline_id bigint CONSTRAINT tr_serieclasseprofdiscipline_profdis_profdiscipline_id_not_null NOT NULL
);


--
-- Name: tr_serieniveaudiscipline_disciplinequantums; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tr_serieniveaudiscipline_disciplinequantums (
    serieniveaudiscipline_id bigint CONSTRAINT tr_serieniveaudiscipline_disc_serieniveaudiscipline_id_not_null NOT NULL,
    disciplinequantums_id bigint CONSTRAINT tr_serieniveaudiscipline_discipl_disciplinequantums_id_not_null NOT NULL
);


--
-- Name: tr_user_profile; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tr_user_profile (
    user_id bigint NOT NULL,
    profile_id bigint NOT NULL
);


--
-- Name: tr_user_role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tr_user_role (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


--
-- Name: traitementmutation_statut; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.traitementmutation_statut (
    statutmut_id bigint,
    traitement_mutation_id bigint NOT NULL
);


--
-- Name: traitementpermutation_statut; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.traitementpermutation_statut (
    statutpermu_id bigint,
    traitement_permutation_id bigint NOT NULL
);


--
-- Name: td_priseencharge; Type: TABLE; Schema: schema_affairesociale; Owner: -
--

CREATE TABLE schema_affairesociale.td_priseencharge (
    pec_id bigint NOT NULL,
    pec_datedemande date,
    pec_datemofifiied date,
    pec_motifmodification character varying(255),
    pec_motifrejet character varying(255),
    pec_numerodemande character varying(255),
    pec_objetdemande character varying(255),
    pec_bur_id bigint,
    pec_cfp_id bigint,
    pec_dir_id bigint,
    pec_div_id bigint,
    pec_eff_id bigint,
    pec_etab_id bigint,
    pec_ia_id bigint,
    pec_ief_id bigint,
    pec_region_id bigint,
    pec_ser_id bigint,
    pec_stat_id bigint,
    pec_type_id bigint,
    pec_utilisateur_id bigint
);


--
-- Name: td_traitementpriseencharge; Type: TABLE; Schema: schema_affairesociale; Owner: -
--

CREATE TABLE schema_affairesociale.td_traitementpriseencharge (
    traitement_id bigint NOT NULL,
    traitement_date date,
    traitement_dernieretatpec character varying(255),
    pec_id bigint,
    traitement_etatactuel character varying(255),
    traitant_id bigint
);


--
-- Name: tp_statutpriseencharge; Type: TABLE; Schema: schema_affairesociale; Owner: -
--

CREATE TABLE schema_affairesociale.tp_statutpriseencharge (
    stat_id bigint NOT NULL,
    stat_code character varying(100),
    stat_libelle character varying(100)
);


--
-- Name: tp_typepriseencharge; Type: TABLE; Schema: schema_affairesociale; Owner: -
--

CREATE TABLE schema_affairesociale.tp_typepriseencharge (
    typepec_id bigint NOT NULL,
    type_code character varying(100),
    type_libelle character varying(100)
);


--
-- Name: td_acte; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_acte (
    acte_id bigint NOT NULL,
    acte_commentaire character varying(255),
    acte_datedebut date,
    acte_datedemandeacte date,
    acte_datefin date,
    acte_emailstraitant character varying(255)[],
    acte_isactivated boolean,
    acte_isdeleted boolean DEFAULT false,
    acte_motifmodification character varying(255),
    acte_motifrejet character varying(255),
    acte_niveau integer,
    acte_profildevanttraiter character varying(25),
    acte_referenceacte character varying(255),
    acte_typeagent character varying(255),
    acte_typeetab character varying(255),
    acte_ag_id bigint,
    acte_bur_id bigint,
    acte_cfp_id bigint,
    currentbordereau_bord_id bigint,
    acte_dir_id bigint,
    acte_div_id bigint,
    acte_eff_id bigint,
    acte_etab_id bigint,
    acte_ia_id bigint,
    acte_ief_id bigint,
    predbordereau_bord_id bigint,
    acte_ser_id bigint,
    statut_acte_id bigint,
    typeag_id bigint,
    typeaa_id bigint,
    type_acte_id bigint,
    dossier_agent_id bigint
);


--
-- Name: td_actualite; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_actualite (
    actu_id bigint NOT NULL,
    actu_contenu bytea NOT NULL,
    actu_date_publication date NOT NULL,
    actu_is_activated boolean,
    actu_is_deleted boolean,
    actu_resume character varying(250),
    actu_titre character varying(150),
    cat_actu_id bigint NOT NULL,
    image_id bigint,
    type_art_id bigint NOT NULL
);


--
-- Name: td_agent; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_agent (
    ag_id bigint NOT NULL,
    ag_date_recrutement date NOT NULL,
    ag_poste_actuel character varying(15) NOT NULL,
    ag_situation_matrimoniale character varying(50) NOT NULL,
    ag_isdeleted boolean,
    deconcentredlevelid bigint,
    dosid bigint
);


--
-- Name: td_avancement; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_avancement (
    avan_id bigint NOT NULL,
    avan_datepriseservice date NOT NULL,
    avan_isdeleted boolean,
    avan_ordreservice character varying(100),
    avan_posteoccupe character varying(100),
    piecejointes_id bigint
);


--
-- Name: td_besoinenpersonnel; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_besoinenpersonnel (
    id bigint NOT NULL,
    besoinenper_annee character varying(100),
    besoinenper_commentaire character varying(100),
    besoinenper_deficit integer,
    cor_id bigint NOT NULL,
    etablissement_id bigint NOT NULL,
    grade_id bigint NOT NULL,
    ia_id bigint NOT NULL,
    ief_id bigint,
    reg_id bigint NOT NULL,
    utilisateur_id bigint
);


--
-- Name: td_diplome; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_diplome (
    dip_id bigint NOT NULL,
    dip_date_obtention date NOT NULL,
    dip_nom character varying(50) NOT NULL,
    dip_is_deleted boolean,
    piecejointes_id bigint,
    dossier_agent_id bigint
);


--
-- Name: td_disciplinequantum; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_disciplinequantum (
    id bigint NOT NULL,
    discquan_quantum integer,
    discipline_id bigint
);


--
-- Name: td_dossieragent; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_dossieragent (
    dos_id bigint NOT NULL,
    dos_is_deleted boolean,
    userid bigint
);


--
-- Name: td_etatcivil; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_etatcivil (
    etat_id bigint NOT NULL,
    etat_is_deleted boolean,
    nom_doc character varying(150) NOT NULL,
    nom_fichier character varying(50) NOT NULL,
    taille bigint NOT NULL,
    piecejointes_id bigint,
    dossier_agent_id bigint
);


--
-- Name: td_fichesynoptique; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_fichesynoptique (
    id bigint NOT NULL,
    deconcentredlevelid bigint
);


--
-- Name: td_imputationoubulletin; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_imputationoubulletin (
    imp_id bigint NOT NULL,
    date_imputation date NOT NULL,
    imputation_generee character varying(50),
    dos_is_deleted boolean,
    nom_beneficiere character varying(50) NOT NULL,
    numero_demande bigint,
    prenom_beneficiere character varying(50) NOT NULL,
    status_beneficiere character varying(50) NOT NULL,
    type_demande character varying(50) NOT NULL,
    userid bigint NOT NULL,
    created_by_id bigint
);


--
-- Name: td_mutation; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_mutation (
    id bigint NOT NULL,
    mut_bordereautransmissionchefbur character varying(100),
    mut_bordereautransmissionchefdiv character varying(100),
    mut_bordereautransmissionce character varying(100),
    mut_bordereautransmissioncfp character varying(100),
    mut_bordereautransmissionchefserv character varying(100),
    mut_bordereautransmissiondgpeec character varying(100),
    mut_bordereautransmissiondrh character varying(100),
    mut_bordereautransmissioneff character varying(100),
    mut_bordereautransmissionia character varying(100),
    mut_bordereautransmissionief character varying(100),
    mut_commentaire character varying(100),
    mut_currentbordereautransmission character varying(100),
    mut_datedemande date,
    mut_destinatairetype character varying(255),
    mut_emailstraitant character varying(255)[],
    mut_numeroref character varying(20),
    mut_0rdreservice character varying(100),
    mut_osgenerated boolean,
    mut_profildevanttraiter character varying(25),
    bureau_id bigint,
    utilisateur_id bigint,
    direction_id bigint,
    division_id bigint,
    etablissement_id bigint,
    ia_id bigint,
    ief_id bigint,
    originedemandeurlog_id bigint,
    reg_id bigint NOT NULL,
    service_id bigint,
    traitementmutation_id bigint,
    mut_dossier_signe character varying(255)
);


--
-- Name: td_originedemandeurlog; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_originedemandeurlog (
    id bigint NOT NULL,
    originedemlog_type character varying(255),
    bureau_id bigint,
    direction_id bigint,
    division_id bigint,
    etablissement_id bigint,
    ia_id bigint,
    ief_id bigint,
    reg_id bigint,
    service_id bigint
);


--
-- Name: td_permutation; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_permutation (
    permu_id bigint NOT NULL,
    date_permutation date NOT NULL,
    date_validation date,
    permu_email_traitant character varying(255)[],
    isactive boolean,
    perm_is_deleted boolean,
    permu_niveau integer,
    ordre_service character varying(150),
    validateetabdemandeur boolean,
    validateetabreceveur boolean,
    validateiademandeur boolean,
    validateiareceveur boolean,
    validateiefdemandeur boolean,
    validateiefreceveur boolean,
    etab_demandeur bigint NOT NULL,
    etab_receveur bigint NOT NULL,
    ia_demandeur bigint NOT NULL,
    ia_receveur bigint NOT NULL,
    ief_demandeur bigint,
    ief_receveur bigint,
    traitementpermutation_traite_id bigint,
    user1_id bigint NOT NULL,
    user2_id bigint NOT NULL,
    motif_permutation character varying(500)
);


--
-- Name: td_piecejoint; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_piecejoint (
    pj_id bigint NOT NULL
);


--
-- Name: td_situation_administrative; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_situation_administrative (
    sit_id bigint NOT NULL,
    dip_date_obtention date NOT NULL,
    sit_is_deleted boolean NOT NULL,
    numero_acte character varying(20) NOT NULL,
    typeag_id bigint,
    typeaa_id bigint,
    piecejointes_id bigint,
    type_acte_id bigint,
    dossier_agent_id bigint
);


--
-- Name: td_traitement_permutation; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_traitement_permutation (
    traite_id bigint NOT NULL,
    traitpermu_date date,
    trait_idpermutation bigint,
    traitpermu_motif character varying(100),
    bordereauvalidation_id bigint,
    statutpermu_id bigint,
    utilisateur_id bigint
);


--
-- Name: td_traitementacte; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_traitementacte (
    traitement_id bigint NOT NULL,
    traitement_date date,
    traitement_etatactuel character varying(255),
    acte_id bigint,
    traitant_id bigint
);


--
-- Name: td_traitementmutation; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.td_traitementmutation (
    id bigint NOT NULL,
    traitmut_date date,
    traitmut_idmutation bigint,
    traitmut_motif character varying(100),
    utilisateur_id bigint,
    traitmut_dossier_signe character varying(255)
);


--
-- Name: tp_bordereau; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_bordereau (
    bord_id bigint NOT NULL,
    bord_acte bigint,
    bord_fic character varying(100),
    utilisateur_id bigint
);


--
-- Name: tp_categorieactualite; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_categorieactualite (
    cat_actu_id bigint NOT NULL,
    codecategorie character varying(50),
    libelle character varying(50)
);


--
-- Name: tp_classe; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_classe (
    id bigint NOT NULL,
    cla_code character varying(10),
    cla_libelle character varying(30)
);


--
-- Name: tp_diplome_list; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_diplome_list (
    dip_id bigint NOT NULL,
    description character varying(150),
    nom_diplome character varying(50) NOT NULL
);


--
-- Name: tp_discipline; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_discipline (
    id bigint NOT NULL,
    disc_code character varying(10),
    disc_libelle character varying(30)
);


--
-- Name: tp_filiere; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_filiere (
    id bigint NOT NULL,
    fil_code character varying(80),
    fil_libelle character varying(80)
);


--
-- Name: tp_formation_prof; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_formation_prof (
    id bigint NOT NULL,
    form_code character varying(10),
    form_libelle character varying(50)
);


--
-- Name: tp_niveau; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_niveau (
    id bigint NOT NULL,
    form_code character varying(10),
    form_libelle character varying(30),
    formation_pro_id bigint
);


--
-- Name: tp_serie; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_serie (
    id bigint NOT NULL,
    serie_code character varying(10),
    serie_libelle character varying(30),
    formation_pro_id bigint
);


--
-- Name: tp_status_permutation; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_status_permutation (
    status_permutation_id bigint NOT NULL,
    status_code character varying(50) NOT NULL,
    status_libelle character varying(50) NOT NULL
);


--
-- Name: tp_statutacte; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_statutacte (
    stat_id bigint NOT NULL,
    stat_code character varying(100),
    stat_libelle character varying(100)
);


--
-- Name: tp_statutbesoinenpersonnel; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_statutbesoinenpersonnel (
    id bigint NOT NULL,
    statutbep_code character varying(10),
    statutbep_libelle character varying(30)
);


--
-- Name: tp_statutmutation; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_statutmutation (
    id bigint NOT NULL,
    statutmut_code character varying(10),
    statutmut_libelle character varying(30)
);


--
-- Name: tp_statuttraitementacte; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_statuttraitementacte (
    stattraitement_id bigint NOT NULL,
    stattraitement_code character varying(100),
    stattraitement_libelle character varying(100),
    stattraitement_niveau character varying(100)
);


--
-- Name: tp_type_article; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_type_article (
    type_art_id bigint NOT NULL,
    libelle character varying(50),
    codearticle character varying(20)
);


--
-- Name: tp_typeaa; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_typeaa (
    typeaa_id bigint NOT NULL,
    typeaa_code character varying(100),
    typeaa_libelle character varying(100),
    type_sortie character varying(255)
);


--
-- Name: tp_typeacte; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_typeacte (
    type_id bigint NOT NULL,
    type_code character varying(100),
    type_libelle character varying(100)
);


--
-- Name: tp_typeag; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tp_typeag (
    typeag_id bigint NOT NULL,
    typeag_code character varying(100),
    typeag_libelle character varying(100),
    type_sortie character varying(255)
);


--
-- Name: tr_bepfilierediscipline; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tr_bepfilierediscipline (
    id bigint NOT NULL,
    filiere_id bigint
);


--
-- Name: tr_besoinennombrediscipline; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tr_besoinennombrediscipline (
    id bigint NOT NULL,
    besoinennbredisc_nombredepersonne bigint,
    discipline_id bigint
);


--
-- Name: tr_classeprofdiscipline; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tr_classeprofdiscipline (
    id bigint NOT NULL,
    claprofdisc_nomclasse character varying(20),
    claprofdisc_quantum integer
);


--
-- Name: tr_filierediscipline; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tr_filierediscipline (
    id bigint NOT NULL,
    fildisc_quantum integer,
    filiere_id bigint,
    niveau_id bigint
);


--
-- Name: tr_profdiscipline; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tr_profdiscipline (
    id bigint NOT NULL,
    deconcentratedlevel_id bigint
);


--
-- Name: tr_serieclasseprofdiscipline; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tr_serieclasseprofdiscipline (
    id bigint NOT NULL,
    serieclaprofdisc_nomclasse character varying(20),
    serieclaprofdisc_quantum integer,
    serie_id bigint
);


--
-- Name: tr_serieniveaudiscipline; Type: TABLE; Schema: schema_carriere; Owner: -
--

CREATE TABLE schema_carriere.tr_serieniveaudiscipline (
    id bigint NOT NULL,
    serienivdisc_quantum integer,
    niveau_id bigint,
    serie_id bigint
);


--
-- Name: td_attestationstage; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_attestationstage (
    attestation_id bigint NOT NULL,
    attestation_commentaire oid,
    attestation_demandestage bigint,
    piecesjoint_id bigint,
    attestation_utilisateur bigint
);


--
-- Name: td_avisdemandestage; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_avisdemandestage (
    avis_id bigint NOT NULL,
    avis_contenu character varying(255) NOT NULL,
    avis_date date,
    avis_centrallevel bigint,
    avis_demandestage bigint
);


--
-- Name: td_campagne; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_campagne (
    cam_id bigint NOT NULL,
    cam_datedebut date NOT NULL,
    cam_datefin date NOT NULL,
    deleted boolean NOT NULL,
    cam_nom character varying(255) NOT NULL,
    cam_statut character varying(255),
    centrallevel_id bigint
);


--
-- Name: td_convocation; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_convocation (
    id bigint NOT NULL,
    nom_fichier character varying(255) NOT NULL,
    formation_id bigint,
    tdr_id bigint
);


--
-- Name: td_convocation_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_convocation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_convocation_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_convocation_id_seq OWNED BY schema_formation.td_convocation.id;


--
-- Name: td_courrier; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_courrier (
    courrier_id bigint NOT NULL,
    courrier_createdat date,
    deleted boolean NOT NULL,
    cou_nomtypecourrier character varying(20),
    cou_other character varying(255),
    courrier_reference character varying(255),
    cou_statut character varying(255),
    cou_typecourrier character varying(20),
    courrier_centrallevel bigint,
    courrier_direction bigint,
    courrier_division bigint,
    courrier_typedemande bigint,
    CONSTRAINT td_courrier_cou_nomtypecourrier_check CHECK (((cou_nomtypecourrier)::text = ANY (ARRAY[('ENTRANT'::character varying)::text, ('SORTANT'::character varying)::text]))),
    CONSTRAINT td_courrier_cou_typecourrier_check CHECK (((cou_typecourrier)::text = ANY (ARRAY[('ENTRANT'::character varying)::text, ('SORTANT'::character varying)::text])))
);


--
-- Name: td_demandestage; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_demandestage (
    demande_id bigint NOT NULL,
    demande_adresse character varying(255) NOT NULL,
    demande_commentaire character varying(255),
    demande_datedebut date,
    demande_datefin date,
    demande_datedenaissance date NOT NULL,
    deleted boolean NOT NULL,
    demande_discipline character varying(80),
    demande_haveattestation boolean,
    demande_haveauthorisationstage boolean,
    demande_haverapport boolean,
    demande_lieudenaissance character varying(255) NOT NULL,
    demande_mail character varying(100) NOT NULL,
    demande_nomdemandeur character varying(80) NOT NULL,
    demande_numero character varying(255),
    demande_objet character varying(255) NOT NULL,
    demande_prenomdemandeur character varying(80) NOT NULL,
    demande_tel character varying(30) NOT NULL,
    demande_bureau bigint,
    demande_centrallevel bigint,
    demande_direction bigint,
    demande_division bigint,
    demande_niveauscolaire bigint,
    demande_service bigint,
    demande_status bigint
);


--
-- Name: td_disciplinestage; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_disciplinestage (
    discipline_id bigint NOT NULL,
    discipline_code character varying(20),
    discipline_deleted boolean,
    discipline_libelle character varying(100)
);


--
-- Name: td_expressiondebesoin; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_expressiondebesoin (
    exp_id bigint NOT NULL,
    exp_besoin character varying(255) NOT NULL,
    exp_date date NOT NULL,
    deleted boolean NOT NULL,
    exp_motif character varying(250) NOT NULL,
    exp_reference character varying(50),
    statut character varying(255),
    exp_campagne bigint,
    exp_utilisateur bigint,
    CONSTRAINT td_expressiondebesoin_statut_check CHECK (((statut)::text = ANY (ARRAY[('NON_TRAITER'::character varying)::text, ('TRAITER'::character varying)::text])))
);


--
-- Name: td_fichiercanditure; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_fichiercanditure (
    id bigint NOT NULL,
    commentaire character varying(2000),
    chefeff_id bigint,
    formation_id bigint
);


--
-- Name: td_fichiercanditure_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_fichiercanditure_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_fichiercanditure_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_fichiercanditure_id_seq OWNED BY schema_formation.td_fichiercanditure.id;


--
-- Name: td_formation; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_formation (
    id bigint NOT NULL,
    cout character varying(200),
    date_debut date,
    date_envoi date,
    date_fin date,
    date_reception date,
    description character varying(2000),
    duree character varying(200),
    eff_code character varying(200),
    intitule character varying(200),
    nombre_place integer,
    prestataires character varying(200),
    reference character varying(2000),
    specialite_code character varying(200),
    cahier_charge_id bigint,
    statut_formation_id bigint,
    theme_formation_id bigint,
    type_formation_id bigint
);


--
-- Name: td_formation_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_formation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_formation_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_formation_id_seq OWNED BY schema_formation.td_formation.id;


--
-- Name: td_offretechniquefinanciere; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_offretechniquefinanciere (
    id bigint NOT NULL,
    commentaire character varying(2000),
    chefeff_id bigint,
    formation_id bigint,
    statut_offre_technique_id bigint
);


--
-- Name: td_offretechniquefinanciere_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_offretechniquefinanciere_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_offretechniquefinanciere_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_offretechniquefinanciere_id_seq OWNED BY schema_formation.td_offretechniquefinanciere.id;


--
-- Name: td_participant; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_participant (
    id bigint NOT NULL,
    participant_id bigint,
    formation_id bigint
);


--
-- Name: td_participant_definitif; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_participant_definitif (
    id bigint NOT NULL,
    admis boolean,
    assidu boolean,
    commentaire character varying(200),
    competences boolean,
    direction character varying(200),
    division character varying(200),
    matricule character varying(200),
    nom_participant character varying(200),
    numero_demande character varying(200),
    formation_id bigint
);


--
-- Name: td_participant_definitif_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_participant_definitif_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_participant_definitif_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_participant_definitif_id_seq OWNED BY schema_formation.td_participant_definitif.id;


--
-- Name: td_participant_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_participant_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_participant_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_participant_id_seq OWNED BY schema_formation.td_participant.id;


--
-- Name: td_participation; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_participation (
    id bigint NOT NULL,
    numero_demande character varying(200),
    central_level_id bigint,
    formation_id bigint
);


--
-- Name: td_participation_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_participation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_participation_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_participation_id_seq OWNED BY schema_formation.td_participation.id;


--
-- Name: td_planformation; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_planformation (
    id bigint NOT NULL,
    commentaire character varying(100),
    date_debut date NOT NULL,
    date_fin date NOT NULL,
    date_publication date,
    ref character varying(100),
    titre character varying(100) NOT NULL,
    created_by bigint NOT NULL,
    statut_plan_formation_id bigint
);


--
-- Name: td_planformation_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_planformation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_planformation_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_planformation_id_seq OWNED BY schema_formation.td_planformation.id;


--
-- Name: td_planningformation; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_planningformation (
    id bigint NOT NULL,
    commentaire character varying(2000),
    formation_id bigint
);


--
-- Name: td_planningformation_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_planningformation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_planningformation_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_planningformation_id_seq OWNED BY schema_formation.td_planningformation.id;


--
-- Name: td_pvexamen; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_pvexamen (
    id bigint NOT NULL,
    rapport_file_id bigint,
    formation_id bigint
);


--
-- Name: td_pvexamen_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_pvexamen_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_pvexamen_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_pvexamen_id_seq OWNED BY schema_formation.td_pvexamen.id;


--
-- Name: td_rapport; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_rapport (
    id bigint NOT NULL,
    commentaire character varying(2000),
    formation_id bigint
);


--
-- Name: td_rapport_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_rapport_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_rapport_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_rapport_id_seq OWNED BY schema_formation.td_rapport.id;


--
-- Name: td_rapportstage; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_rapportstage (
    rapport_id bigint NOT NULL,
    rapport_commentaire oid,
    rapport_demandestage bigint,
    piecesjoint_id bigint,
    rapport_utilisateur bigint
);


--
-- Name: td_session; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_session (
    id bigint NOT NULL,
    commentaire character varying(2000),
    date_debut date,
    date_fin date,
    rapport_file_id bigint,
    formation_id bigint
);


--
-- Name: td_session_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_session_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_session_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_session_id_seq OWNED BY schema_formation.td_session.id;


--
-- Name: td_statut_formation; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_statut_formation (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    libelle character varying(255) NOT NULL
);


--
-- Name: td_statut_formation_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_statut_formation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_statut_formation_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_statut_formation_id_seq OWNED BY schema_formation.td_statut_formation.id;


--
-- Name: td_statut_offre_technique; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_statut_offre_technique (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    libelle character varying(255) NOT NULL
);


--
-- Name: td_statut_offre_technique_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_statut_offre_technique_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_statut_offre_technique_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_statut_offre_technique_id_seq OWNED BY schema_formation.td_statut_offre_technique.id;


--
-- Name: td_statut_planformation; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_statut_planformation (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    libelle character varying(255) NOT NULL
);


--
-- Name: td_statut_planformation_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_statut_planformation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_statut_planformation_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_statut_planformation_id_seq OWNED BY schema_formation.td_statut_planformation.id;


--
-- Name: td_tableau_suivi; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_tableau_suivi (
    id bigint NOT NULL,
    abscences integer NOT NULL,
    nbrsession integer,
    presences integer NOT NULL,
    formation_id bigint
);


--
-- Name: td_tableau_suivi_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_tableau_suivi_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_tableau_suivi_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_tableau_suivi_id_seq OWNED BY schema_formation.td_tableau_suivi.id;


--
-- Name: td_themeformation; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_themeformation (
    id bigint NOT NULL,
    bailleur character varying(255) NOT NULL,
    budget character varying(255) NOT NULL,
    duree character varying(100) NOT NULL,
    libelle character varying(255) NOT NULL,
    modalite character varying(255) NOT NULL,
    operateur character varying(255) NOT NULL,
    direction_id bigint,
    id_plan_formation bigint,
    id_respponsable_suivi bigint
);


--
-- Name: td_themeformation_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_themeformation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_themeformation_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_themeformation_id_seq OWNED BY schema_formation.td_themeformation.id;


--
-- Name: td_traitementcampagne; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_traitementcampagne (
    traitcam_id bigint NOT NULL,
    traitcam_activated boolean,
    traitcam_campagne bigint,
    traitcam_statutcampagne bigint,
    traitcam_utilisateur bigint
);


--
-- Name: td_traitementcourrier; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_traitementcourrier (
    traitcou_id bigint NOT NULL,
    traitcam_activated boolean,
    traitcou_courrier bigint,
    traitcou_statutcourrier bigint,
    traitcou_utilisateur bigint
);


--
-- Name: td_traitementdemandestage; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_traitementdemandestage (
    traitdeman_id bigint NOT NULL,
    traitdeman_activated boolean,
    traitdeman_utilisateur bigint,
    traitdeman_demandestage bigint,
    traitexp_statutdemandestage bigint
);


--
-- Name: td_traitementexpressiondebesoin; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_traitementexpressiondebesoin (
    traitexp_id bigint NOT NULL,
    traitexp_activated boolean,
    traitexp_datetraitement date,
    traitexp_themeprovisoire character varying(100),
    traitexp_expressiondebesoin bigint,
    traitexp_statutexpressiondebesoin bigint,
    traitexp_utilisateur bigint
);


--
-- Name: td_type_formation; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.td_type_formation (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    libelle character varying(255) NOT NULL
);


--
-- Name: td_type_formation_id_seq; Type: SEQUENCE; Schema: schema_formation; Owner: -
--

CREATE SEQUENCE schema_formation.td_type_formation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_type_formation_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_formation; Owner: -
--

ALTER SEQUENCE schema_formation.td_type_formation_id_seq OWNED BY schema_formation.td_type_formation.id;


--
-- Name: tp_niveauscolaire; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.tp_niveauscolaire (
    niveau_id bigint NOT NULL,
    niveau_code character varying(20),
    niveau_deleted boolean,
    niveau_libelle character varying(100)
);


--
-- Name: tp_statutcampagne; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.tp_statutcampagne (
    stcam_id bigint NOT NULL,
    stcam_code character varying(20) NOT NULL,
    isdeleted boolean NOT NULL,
    stcam_libelle character varying(100) NOT NULL
);


--
-- Name: tp_statutcourrier; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.tp_statutcourrier (
    stcou_id bigint NOT NULL,
    stcou_code character varying(20) NOT NULL,
    isdeleted boolean NOT NULL,
    stcou_libelle character varying(100) NOT NULL
);


--
-- Name: tp_statutdemandestage; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.tp_statutdemandestage (
    statudeman_id bigint NOT NULL,
    statudeman_code character varying(20),
    statudeman_deleted boolean,
    statudeman_libelle character varying(100)
);


--
-- Name: tp_statutexpressiondebesoin; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.tp_statutexpressiondebesoin (
    stexp_id bigint NOT NULL,
    stexp_code character varying(20) NOT NULL,
    stexp_deleted boolean,
    stexp_libelle character varying(100) NOT NULL
);


--
-- Name: tp_typedemandecourrier; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.tp_typedemandecourrier (
    typedemcou_id bigint NOT NULL,
    typedemcou_code character varying(20) NOT NULL,
    typedemcou_deleted boolean,
    typedemcou_libelle character varying(100) NOT NULL,
    typedemcou_nomtypecourrier character varying(20),
    typedemcou_division bigint,
    CONSTRAINT tp_typedemandecourrier_typedemcou_nomtypecourrier_check CHECK (((typedemcou_nomtypecourrier)::text = ANY (ARRAY[('ENTRANT'::character varying)::text, ('SORTANT'::character varying)::text])))
);


--
-- Name: tr_theme_formation_profile; Type: TABLE; Schema: schema_formation; Owner: -
--

CREATE TABLE schema_formation.tr_theme_formation_profile (
    theme_id bigint NOT NULL,
    profile_id bigint NOT NULL
);


--
-- Name: td_actionpta; Type: TABLE; Schema: schema_pta; Owner: -
--

CREATE TABLE schema_pta.td_actionpta (
    id bigint NOT NULL,
    act_label_action character varying(100),
    act_num_action character varying(100),
    pta_action_id bigint
);


--
-- Name: td_initialpta; Type: TABLE; Schema: schema_pta; Owner: -
--

CREATE TABLE schema_pta.td_initialpta (
    id bigint NOT NULL,
    init_pta_date date,
    init_pta_name character varying(100),
    pta_number character varying(75),
    init_pta_direction_id bigint
);


--
-- Name: td_modecalcul; Type: TABLE; Schema: schema_pta; Owner: -
--

CREATE TABLE schema_pta.td_modecalcul (
    id bigint NOT NULL,
    mode_calcul_frequence character varying(100),
    mode_calcul_collect character varying(100),
    mode_calcul_mode character varying(100),
    mode_calcul_source character varying(100),
    result_id bigint
);


--
-- Name: td_parametre; Type: TABLE; Schema: schema_pta; Owner: -
--

CREATE TABLE schema_pta.td_parametre (
    param_id bigint NOT NULL,
    param_date date,
    param_libelle character varying(80) NOT NULL,
    param_numero character varying(15),
    param_responsableactivite character varying(255),
    param_statut character varying(255),
    CONSTRAINT td_parametre_param_responsableactivite_check CHECK (((param_responsableactivite)::text = 'DRH'::text))
);


--
-- Name: td_plandetravail; Type: TABLE; Schema: schema_pta; Owner: -
--

CREATE TABLE schema_pta.td_plandetravail (
    id bigint NOT NULL,
    pta_inial_pta_id bigint
);


--
-- Name: td_reportrealisation; Type: TABLE; Schema: schema_pta; Owner: -
--

CREATE TABLE schema_pta.td_reportrealisation (
    id bigint NOT NULL,
    report_target integer DEFAULT 100,
    report_date_debut date,
    report_date_fin date,
    report_observation text,
    report_resume character varying(255),
    report_rate_acheived integer
);


--
-- Name: td_resultatpta; Type: TABLE; Schema: schema_pta; Owner: -
--

CREATE TABLE schema_pta.td_resultatpta (
    id bigint NOT NULL,
    result_target integer DEFAULT 100,
    result_label character varying(100),
    result_num character varying(100),
    result_rate_acheived integer,
    result_act_pta_id bigint
);


--
-- Name: td_subaction; Type: TABLE; Schema: schema_pta; Owner: -
--

CREATE TABLE schema_pta.td_subaction (
    id bigint NOT NULL,
    sub_action_budget numeric(38,2),
    sub_action_date_debut date,
    sub_action_date_fin date,
    sub_action_label character varying(100),
    sub_action_moyen_rhl character varying(100),
    sub_action_numn character varying(100),
    sub_action_finacement character varying(100),
    sub_action_indicateur_id bigint,
    sub_action_mode_id bigint,
    sub_action_report_id bigint,
    sub_action_result_id bigint,
    sub_action_user_id bigint
);


--
-- Name: td_tdsequence; Type: TABLE; Schema: schema_pta; Owner: -
--

CREATE TABLE schema_pta.td_tdsequence (
    id integer NOT NULL,
    seq_annee integer NOT NULL,
    seq_numero integer NOT NULL
);


--
-- Name: td_traitementparametre; Type: TABLE; Schema: schema_pta; Owner: -
--

CREATE TABLE schema_pta.td_traitementparametre (
    traitparam_id bigint NOT NULL,
    traitparam_activated boolean,
    traitparam_parametre bigint,
    traitparam_statutcourrier bigint,
    traitparam_utilisateur bigint
);


--
-- Name: tp_statutparametre; Type: TABLE; Schema: schema_pta; Owner: -
--

CREATE TABLE schema_pta.tp_statutparametre (
    stuparam_id bigint NOT NULL,
    stuparam_code character varying(20) NOT NULL,
    isdeleted boolean NOT NULL,
    stuparam_libelle character varying(100) NOT NULL
);


--
-- Name: notification_readers; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.notification_readers (
    notification_id bigint NOT NULL,
    user_id bigint NOT NULL
);


--
-- Name: td_archived_utilisateur; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.td_archived_utilisateur (
    id bigint NOT NULL,
    uti_createdby bigint,
    uti_createddate timestamp(6) without time zone,
    uti_modifiedby bigint,
    uti_modifieddate timestamp(6) without time zone,
    user_archived_adresse character varying(150),
    user_cni character varying(150),
    user_date_corp date,
    user_archived_date_entree date,
    user_date_fonc_pub date,
    user_date_enseign date,
    user_date_etan date,
    user_date_service date,
    user_date_naissance date,
    user_archived_pmail character varying(200),
    user_archived_firstlog boolean DEFAULT true,
    user_archived boolean DEFAULT false,
    user_is_fonctionnaire boolean DEFAULT false,
    user_lieu_naissance character varying(100),
    user_archived_matricule character varying(150),
    user_matricule_contractuel character varying(150),
    user_matricule_deci character varying(20),
    user_matricule_solde character varying(150),
    user_matricule_vac character varying(20),
    user_nationalite character varying(20),
    user_archived_lastname character varying(25) NOT NULL,
    nombreenfants integer DEFAULT 0,
    user_archived_password character varying(255),
    user_archived_firstname character varying(50),
    user_quantum_horaire integer,
    user_archived_sexe character varying(10),
    user_archived_marital_status character varying(50),
    user_archived_status boolean DEFAULT true,
    user_archived_phonenumber character varying(150),
    user_archived_user_type character varying(10),
    bureau_id bigint,
    corp_id bigint,
    dip_aca_id bigint,
    dip_ped_id bigint,
    dip_prof_id bigint,
    direction_id bigint,
    division_id bigint,
    etablissement_id bigint,
    fonction_id bigint,
    grade_id bigint,
    ia_id bigint,
    ief_id bigint,
    region_id bigint,
    service_id bigint,
    speciality_id bigint,
    structure_id bigint,
    type_mat_id bigint,
    dip_type_poste_id bigint
);


--
-- Name: td_centrallevel; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.td_centrallevel (
    id bigint NOT NULL,
    uti_createdby bigint,
    uti_createddate timestamp(6) without time zone,
    uti_modifiedby bigint,
    uti_modifieddate timestamp(6) without time zone,
    user_adresse character varying(150),
    user_cni character varying(20),
    user_date_corp date,
    user_date_entree date,
    user_date_fonc_pub date,
    user_date_naissance date,
    user_pmail character varying(200),
    uti_firstlog boolean DEFAULT true,
    user_isdeleted boolean,
    user_is_fonctionnaire boolean DEFAULT false,
    user_lieu_naissance character varying(100),
    user_matricule character varying(20),
    user_matricule_contractuel character varying(20),
    user_matricule_solde character varying(20),
    user_nationalite character varying(20),
    user_lastname character varying(25) NOT NULL,
    nombreenfants integer DEFAULT 0,
    user_password character varying(255),
    user_firstname character varying(50),
    user_sexe character varying(10),
    user_marital_status character varying(50),
    user_status boolean DEFAULT true,
    user_phonenumber character varying(20),
    user_user_type character varying(10),
    corp_id bigint,
    dip_aca_id bigint,
    dip_ped_id bigint,
    dip_prof_id bigint,
    fonction_id bigint,
    grade_id bigint,
    region_id bigint,
    type_mat_id bigint,
    dip_type_poste_id bigint,
    bureau_id bigint,
    direction_id bigint,
    division_id bigint,
    service_id bigint,
    user_date_enseign date,
    speciality_id bigint,
    user_matricule_deci character varying(20),
    user_matricule_vac character varying(20),
    user_date_service date
);


--
-- Name: td_contacts; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.td_contacts (
    contact_id bigint NOT NULL,
    cont_commentaire character varying(250),
    cont_email character varying(100),
    cont_nomcomplet character varying(150),
    cont_telephone character varying(20)
);


--
-- Name: td_deconcentratedlevel; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.td_deconcentratedlevel (
    id bigint NOT NULL,
    uti_createdby bigint,
    uti_createddate timestamp(6) without time zone,
    uti_modifiedby bigint,
    uti_modifieddate timestamp(6) without time zone,
    user_adresse character varying(150),
    user_cni character varying(20),
    user_date_corp date,
    user_date_entree date,
    user_date_fonc_pub date,
    user_date_enseign date,
    user_date_service date,
    user_date_naissance date,
    user_pmail character varying(200),
    uti_firstlog boolean DEFAULT true,
    user_is_fonctionnaire boolean DEFAULT false,
    user_lieu_naissance character varying(100),
    user_matricule character varying(20),
    user_matricule_contractuel character varying(20),
    user_matricule_deci character varying(20),
    user_matricule_solde character varying(20),
    user_matricule_vac character varying(20),
    user_nationalite character varying(20),
    user_lastname character varying(25) NOT NULL,
    nombreenfants integer DEFAULT 0,
    user_password character varying(255),
    user_firstname character varying(50),
    user_sexe character varying(10),
    user_marital_status character varying(50),
    user_status boolean DEFAULT true,
    user_phonenumber character varying(20),
    user_user_type character varying(10),
    corp_id bigint,
    dip_aca_id bigint,
    dip_ped_id bigint,
    dip_prof_id bigint,
    fonction_id bigint,
    grade_id bigint,
    region_id bigint,
    speciality_id bigint,
    type_mat_id bigint,
    dip_type_poste_id bigint,
    user_date_etan date,
    user_quantum_horaire integer,
    etablissement_id bigint,
    ia_id bigint,
    ief_id bigint,
    structure_id bigint,
    typesystemeens_id bigint
);


--
-- Name: td_diplome; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.td_diplome (
    dip_id bigint NOT NULL,
    dip_code character varying(15) NOT NULL,
    dip_libelle character varying(100),
    dip_statut boolean DEFAULT true,
    type_diplome_id bigint,
    id bigint NOT NULL,
    code character varying(15),
    label character varying(100)
);


--
-- Name: td_failedmail; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.td_failedmail (
    fai_id bigint NOT NULL,
    fai_createddate timestamp(6) without time zone,
    fai_email character varying(255),
    fai_issent boolean DEFAULT false,
    fai_subject character varying(255),
    fai_text text
);


--
-- Name: td_failedmail_fai_id_seq; Type: SEQUENCE; Schema: schema_utilisateur; Owner: -
--

CREATE SEQUENCE schema_utilisateur.td_failedmail_fai_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: td_failedmail_fai_id_seq; Type: SEQUENCE OWNED BY; Schema: schema_utilisateur; Owner: -
--

ALTER SEQUENCE schema_utilisateur.td_failedmail_fai_id_seq OWNED BY schema_utilisateur.td_failedmail.fai_id;


--
-- Name: td_file; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.td_file (
    id bigint NOT NULL,
    base64 character varying(255),
    download_url character varying(255),
    file_code character varying(255),
    file_size bigint,
    file_type character varying(255),
    generated_name character varying(255),
    idappartenance bigint,
    original_name character varying(255),
    pec_piece_id bigint,
    report_files_id bigint,
    cam_piecejoint bigint,
    imp_id bigint,
    acte_id bigint,
    mutation_id bigint
);


--
-- Name: td_notification; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.td_notification (
    id bigint NOT NULL,
    codeprofile character varying(255),
    date timestamp(6) without time zone,
    iduser bigint,
    isread boolean NOT NULL,
    message character varying(255),
    notreads bigint,
    objet character varying(255)
);


--
-- Name: td_parametre_corpsgrade; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.td_parametre_corpsgrade (
    params_cg_id bigint NOT NULL,
    params_date_param date,
    params_cg_ref character varying(255),
    params_cg_statut boolean,
    corps_grade_id bigint,
    speciality_id bigint
);


--
-- Name: td_parametre_corpsgrade_pk; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.td_parametre_corpsgrade_pk (
    params_cg_id bigint NOT NULL,
    grade_id bigint,
    speciality_id bigint,
    type_matricule_id bigint
);


--
-- Name: td_tdsequence_ref; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.td_tdsequence_ref (
    id integer NOT NULL,
    seq_ref_annee integer NOT NULL,
    seq_ref_numero integer NOT NULL
);


--
-- Name: td_usermanager; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.td_usermanager (
    id bigint NOT NULL,
    uti_createdby bigint,
    uti_createddate timestamp(6) without time zone,
    uti_modifiedby bigint,
    uti_modifieddate timestamp(6) without time zone,
    user_adresse character varying(150),
    user_pmail character varying(200),
    uti_firstlog boolean DEFAULT true,
    user_isdeleted boolean,
    user_matricule character varying(20),
    user_lastname character varying(25) NOT NULL,
    user_password character varying(255),
    user_firstname character varying(50),
    user_sexe character varying(10),
    user_status boolean DEFAULT true,
    user_phonenumber character varying(20),
    user_user_type character varying(10),
    corp_id bigint,
    fonction_id bigint,
    user_marital_status character varying(50),
    grade_id bigint,
    user_date_entree date,
    region_id bigint,
    user_cni character varying(20),
    user_date_corp date,
    user_date_fonc_pub date,
    user_date_naissance date,
    user_is_fonctionnaire boolean DEFAULT false,
    user_lieu_naissance character varying(100),
    user_matricule_contractuel character varying(20),
    user_matricule_solde character varying(20),
    user_nationalite character varying(20),
    nombreenfants integer DEFAULT 0,
    dip_aca_id bigint,
    dip_ped_id bigint,
    dip_prof_id bigint,
    type_mat_id bigint,
    dip_type_poste_id bigint,
    user_date_enseign date,
    speciality_id bigint,
    user_matricule_deci character varying(20),
    user_matricule_vac character varying(20),
    user_date_service date
);


--
-- Name: tp_bureau; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_bureau (
    bur_id bigint NOT NULL,
    bur_code character varying(10),
    bur_libelle character varying(100),
    bur_statut boolean DEFAULT true,
    division_id bigint
);


--
-- Name: tp_cfp; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_cfp (
    cfp_id bigint NOT NULL,
    cfp_code character varying(10),
    cfp_libelle character varying(100),
    ief_id bigint
);


--
-- Name: tp_corps; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_corps (
    cor_id bigint NOT NULL,
    cor_code character varying(200) NOT NULL,
    cor_libelle character varying(100),
    cor_type_statut boolean,
    cor_type_matricule character varying(100)
);


--
-- Name: tp_diplome_aca; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_diplome_aca (
    dip_aca_id bigint NOT NULL,
    dip_aca_code character varying(10) NOT NULL,
    dip_aca_libelle character varying(100),
    dip_aca_statut boolean DEFAULT true,
    diplomes_dip_id bigint
);


--
-- Name: tp_diplome_ped; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_diplome_ped (
    dip_ped_id bigint NOT NULL,
    dip_ped_code character varying(10) NOT NULL,
    dip_ped_libelle character varying(100),
    dip_ped_statut boolean DEFAULT true,
    diplomes_dip_id bigint
);


--
-- Name: tp_diplome_prof; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_diplome_prof (
    dip_prof_id bigint NOT NULL,
    dip_prof_code character varying(10) NOT NULL,
    dip_prof_libelle character varying(100),
    dip_prof_statut boolean DEFAULT true,
    diplomes_dip_id bigint
);


--
-- Name: tp_direction; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_direction (
    dir_id bigint NOT NULL,
    dir_code character varying(10),
    dir_libelle character varying(100),
    dir_statut boolean DEFAULT true,
    region_id bigint
);


--
-- Name: tp_division; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_division (
    div_id bigint NOT NULL,
    div_code character varying(20),
    div_libelle character varying(100),
    div_statut boolean DEFAULT true,
    direction_id bigint,
    sub_action_division_id bigint
);


--
-- Name: tp_eef; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_eef (
    eef_id bigint NOT NULL,
    eef_code character varying(10),
    eef_libelle character varying(100)
);


--
-- Name: tp_eefministere; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_eefministere (
    eefmi_id bigint NOT NULL,
    eefmi_code character varying(10),
    eefmi_libelle character varying(100),
    eefmi_region_id bigint
);


--
-- Name: tp_efspeciality; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_efspeciality (
    eef_spec_id bigint NOT NULL,
    eef_spec_code character varying(10),
    eef_spec_libelle character varying(100),
    eef_id bigint
);


--
-- Name: tp_entity; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_entity (
    ent_id bigint NOT NULL,
    ent_code character varying(10),
    ent_libelle character varying(100)
);


--
-- Name: tp_etablissement_type; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_etablissement_type (
    eta_id bigint NOT NULL,
    eta_code character varying(10),
    eta_libelle character varying(100)
);


--
-- Name: tp_fonction; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_fonction (
    fon_id bigint NOT NULL,
    pro_code character varying(20) NOT NULL,
    pro_libelle character varying(100),
    dir_statut boolean DEFAULT true
);


--
-- Name: tp_grade; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_grade (
    grade_id bigint NOT NULL,
    grade_code character varying(200) NOT NULL,
    grade_libelle character varying(100),
    corps_id bigint
);


--
-- Name: tp_ia; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_ia (
    ia_id bigint NOT NULL,
    ia_code character varying(20),
    ia_libelle character varying(100),
    ia_statut boolean DEFAULT true,
    region_id bigint
);


--
-- Name: tp_ief; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_ief (
    seq_ief bigint NOT NULL,
    ief_code character varying(10),
    ief_libelle character varying(100),
    ief_statut boolean DEFAULT true,
    ia_id bigint
);


--
-- Name: tp_menu; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_menu (
    menu_id bigint NOT NULL,
    men_icontype text,
    men_path character varying(255),
    men_ytitle character varying(255),
    men_type character varying(255)
);


--
-- Name: tp_profile; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_profile (
    pro_id bigint NOT NULL,
    pro_code character varying(50) NOT NULL,
    pro_libelle character varying(100),
    pro_type character varying(255),
    pro_type_bureau character varying(255),
    pro_type_direction character varying(255),
    pro_type_division character varying(255),
    fonctions bigint
);


--
-- Name: tp_profile_menu_child; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_profile_menu_child (
    pmc_id bigint NOT NULL,
    smn_id bigint,
    men_id bigint,
    pro_id bigint
);


--
-- Name: tp_region; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_region (
    reg_id bigint NOT NULL,
    reg_code character varying(10) NOT NULL,
    reg_libelle character varying(100)
);


--
-- Name: tp_service; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_service (
    ser_id bigint NOT NULL,
    ser_code character varying(10),
    ser_libelle character varying(100),
    direction_id bigint
);


--
-- Name: tp_specialiteeef; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_specialiteeef (
    speeef_id bigint NOT NULL,
    etablissement_id bigint,
    speciality_id bigint
);


--
-- Name: tp_speciality; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_speciality (
    spe_id bigint NOT NULL,
    spe_code character varying(20),
    spe_libelle character varying(100),
    spe_statut boolean DEFAULT true
);


--
-- Name: tp_structure; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_structure (
    str_id bigint NOT NULL,
    str_code character varying(10),
    str_libelle character varying(100)
);


--
-- Name: tp_structure_mfpaa; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_structure_mfpaa (
    smfpa_id bigint NOT NULL,
    smfpa_code character varying(10),
    smfpa_libelle character varying(100),
    ief_id bigint
);


--
-- Name: tp_type_diplome; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_type_diplome (
    type_dip_id bigint NOT NULL,
    type_dip_code character varying(10) NOT NULL,
    type_dip_libelle character varying(100)
);


--
-- Name: tp_type_matricule; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_type_matricule (
    type_mat_id bigint NOT NULL,
    type_mat_code character varying(10) NOT NULL,
    type_mat_libelle character varying(100)
);


--
-- Name: tp_type_poste; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_type_poste (
    type_poste_id bigint NOT NULL,
    type_poste_code character varying(10) NOT NULL,
    type_poste_libelle character varying(100)
);


--
-- Name: tp_type_systeme_enseignement; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_type_systeme_enseignement (
    typesystemeens_id bigint NOT NULL,
    typesystemeens_code character varying(10),
    typesystemeens_libelle character varying(100)
);


--
-- Name: tp_typeetablissement; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_typeetablissement (
    eta_id bigint NOT NULL,
    eta_code character varying(10),
    eta_libelle character varying(100),
    eta_statut boolean DEFAULT true,
    ia_id bigint,
    ief_id bigint,
    structure_id bigint,
    typeetablissement_id bigint,
    typesystemeens_id bigint
);


--
-- Name: tp_typesysteme_enseignement; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_typesysteme_enseignement (
    typesystemeens_id bigint NOT NULL,
    typesystemeens_code character varying(10),
    typesystemeens_libelle character varying(100)
);


--
-- Name: tp_typesystemeenseignement; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tp_typesystemeenseignement (
    typesystemeens_id bigint NOT NULL,
    typesystemeens_code character varying(10),
    typesystemeens_libelle character varying(100)
);


--
-- Name: tr_men_sous_menu; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tr_men_sous_menu (
    men_id bigint NOT NULL,
    smn_id bigint NOT NULL
);


--
-- Name: tr_profilemenusousmenu; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tr_profilemenusousmenu (
    pmsm_id bigint NOT NULL,
    men_id bigint,
    profile_id bigint,
    smn_id bigint[]
);


--
-- Name: tr_profilmenu; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tr_profilmenu (
    pro_id bigint NOT NULL,
    men_id bigint NOT NULL
);


--
-- Name: tr_speciality_etablissement; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tr_speciality_etablissement (
    spec_etab_id bigint NOT NULL,
    etablissement_id bigint,
    speciality_id bigint
);


--
-- Name: tr_user_profile; Type: TABLE; Schema: schema_utilisateur; Owner: -
--

CREATE TABLE schema_utilisateur.tr_user_profile (
    user_id bigint NOT NULL,
    profile_id bigint NOT NULL
);


--
-- Name: td_disposableemail dis_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_disposableemail ALTER COLUMN dis_id SET DEFAULT nextval('public.td_disposableemail_dis_id_seq'::regclass);


--
-- Name: td_failedmail fai_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_failedmail ALTER COLUMN fai_id SET DEFAULT nextval('public.td_failedmail_fai_id_seq'::regclass);


--
-- Name: td_utilisateur uti_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_utilisateur ALTER COLUMN uti_id SET DEFAULT nextval('public.td_utilisateur_uti_id_seq'::regclass);


--
-- Name: tp_menu men_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_menu ALTER COLUMN men_id SET DEFAULT nextval('public.tp_menu_men_id_seq'::regclass);


--
-- Name: tp_profil pro_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_profil ALTER COLUMN pro_id SET DEFAULT nextval('public.tp_profil_pro_id_seq'::regclass);


--
-- Name: td_convocation id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_convocation ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_convocation_id_seq'::regclass);


--
-- Name: td_fichiercanditure id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_fichiercanditure ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_fichiercanditure_id_seq'::regclass);


--
-- Name: td_formation id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_formation ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_formation_id_seq'::regclass);


--
-- Name: td_offretechniquefinanciere id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_offretechniquefinanciere ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_offretechniquefinanciere_id_seq'::regclass);


--
-- Name: td_participant id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_participant ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_participant_id_seq'::regclass);


--
-- Name: td_participant_definitif id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_participant_definitif ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_participant_definitif_id_seq'::regclass);


--
-- Name: td_participation id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_participation ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_participation_id_seq'::regclass);


--
-- Name: td_planformation id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_planformation ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_planformation_id_seq'::regclass);


--
-- Name: td_planningformation id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_planningformation ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_planningformation_id_seq'::regclass);


--
-- Name: td_pvexamen id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_pvexamen ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_pvexamen_id_seq'::regclass);


--
-- Name: td_rapport id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_rapport ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_rapport_id_seq'::regclass);


--
-- Name: td_session id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_session ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_session_id_seq'::regclass);


--
-- Name: td_statut_formation id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_statut_formation ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_statut_formation_id_seq'::regclass);


--
-- Name: td_statut_offre_technique id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_statut_offre_technique ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_statut_offre_technique_id_seq'::regclass);


--
-- Name: td_statut_planformation id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_statut_planformation ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_statut_planformation_id_seq'::regclass);


--
-- Name: td_tableau_suivi id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_tableau_suivi ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_tableau_suivi_id_seq'::regclass);


--
-- Name: td_themeformation id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_themeformation ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_themeformation_id_seq'::regclass);


--
-- Name: td_type_formation id; Type: DEFAULT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_type_formation ALTER COLUMN id SET DEFAULT nextval('schema_formation.td_type_formation_id_seq'::regclass);


--
-- Name: td_failedmail fai_id; Type: DEFAULT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_failedmail ALTER COLUMN fai_id SET DEFAULT nextval('schema_utilisateur.td_failedmail_fai_id_seq'::regclass);


--
-- Name: besoin_en_personnel_statut besoin_en_personnel_statut_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.besoin_en_personnel_statut
    ADD CONSTRAINT besoin_en_personnel_statut_pkey PRIMARY KEY (besoin_en_personnel_id);


--
-- Name: td_account td_account_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_account
    ADD CONSTRAINT td_account_pkey PRIMARY KEY (user_id);


--
-- Name: td_amendment td_amendment_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_amendment
    ADD CONSTRAINT td_amendment_pkey PRIMARY KEY (id);


--
-- Name: td_contract td_contract_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_contract
    ADD CONSTRAINT td_contract_pkey PRIMARY KEY (id);


--
-- Name: td_disposableemail td_disposableemail_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_disposableemail
    ADD CONSTRAINT td_disposableemail_pkey PRIMARY KEY (dis_id);


--
-- Name: td_entite td_entite_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_entite
    ADD CONSTRAINT td_entite_pkey PRIMARY KEY (id);


--
-- Name: td_expressionbesoin td_expressionbesoin_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_expressionbesoin
    ADD CONSTRAINT td_expressionbesoin_pkey PRIMARY KEY (id);


--
-- Name: td_failed_mail td_failed_mail_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_failed_mail
    ADD CONSTRAINT td_failed_mail_pkey PRIMARY KEY (fai_id);


--
-- Name: td_failedmail td_failedmail_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_failedmail
    ADD CONSTRAINT td_failedmail_pkey PRIMARY KEY (fai_id);


--
-- Name: td_fichiercanditure_files td_fichiercanditure_files_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichiercanditure_files
    ADD CONSTRAINT td_fichiercanditure_files_pkey PRIMARY KEY (fichiercandidature_id, files_id);


--
-- Name: td_file td_file_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_file
    ADD CONSTRAINT td_file_pkey PRIMARY KEY (id);


--
-- Name: td_offretechniquefinanciere_files td_offretechniquefinanciere_files_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_offretechniquefinanciere_files
    ADD CONSTRAINT td_offretechniquefinanciere_files_pkey PRIMARY KEY (offretechniquefinanciere_id, files_id);


--
-- Name: td_parapher td_parapher_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_parapher
    ADD CONSTRAINT td_parapher_pkey PRIMARY KEY (id);


--
-- Name: td_planformation_files td_planformation_files_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_planformation_files
    ADD CONSTRAINT td_planformation_files_pkey PRIMARY KEY (planformation_id, files_id);


--
-- Name: td_planningformation_files td_planningformation_files_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_planningformation_files
    ADD CONSTRAINT td_planningformation_files_pkey PRIMARY KEY (planningformation_id, files_id);


--
-- Name: td_rapport_files td_rapport_files_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_rapport_files
    ADD CONSTRAINT td_rapport_files_pkey PRIMARY KEY (rapport_id, files_id);


--
-- Name: td_rapport_pv td_rapport_pv_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_rapport_pv
    ADD CONSTRAINT td_rapport_pv_pkey PRIMARY KEY (rapport_id, pv_id);


--
-- Name: td_role td_role_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_role
    ADD CONSTRAINT td_role_pkey PRIMARY KEY (id);


--
-- Name: td_user td_user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_user
    ADD CONSTRAINT td_user_pkey PRIMARY KEY (id);


--
-- Name: td_utilisateur td_utilisateur_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_utilisateur
    ADD CONSTRAINT td_utilisateur_pkey PRIMARY KEY (uti_id);


--
-- Name: token token_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (id);


--
-- Name: td_disciplinestage tp_discipline_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_disciplinestage
    ADD CONSTRAINT tp_discipline_pkey PRIMARY KEY (discipline_id);


--
-- Name: tp_emergency_level tp_emergency_level_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_emergency_level
    ADD CONSTRAINT tp_emergency_level_pkey PRIMARY KEY (id);


--
-- Name: tp_menu_children tp_menu_children_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_menu_children
    ADD CONSTRAINT tp_menu_children_pkey PRIMARY KEY (menu_menu_id, children_menu_id);


--
-- Name: tp_menu tp_menu_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_menu
    ADD CONSTRAINT tp_menu_pkey PRIMARY KEY (men_id);


--
-- Name: tp_niveauscolaire tp_niveauscolaire_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_niveauscolaire
    ADD CONSTRAINT tp_niveauscolaire_pkey PRIMARY KEY (niveau_id);


--
-- Name: tp_processing tp_processing_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_processing
    ADD CONSTRAINT tp_processing_pkey PRIMARY KEY (id);


--
-- Name: tp_profil tp_profil_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_profil
    ADD CONSTRAINT tp_profil_pkey PRIMARY KEY (pro_id);


--
-- Name: tp_profile_menus tp_profile_menus_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_profile_menus
    ADD CONSTRAINT tp_profile_menus_pkey PRIMARY KEY (profile_pro_id, menus_pmsm_id);


--
-- Name: tp_statutdemandestage tp_statutdemandestage_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_statutdemandestage
    ADD CONSTRAINT tp_statutdemandestage_pkey PRIMARY KEY (statudeman_id);


--
-- Name: tp_typedemandecourrier tp_typedemandecourrier_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_typedemandecourrier
    ADD CONSTRAINT tp_typedemandecourrier_pkey PRIMARY KEY (typedemcou_id);


--
-- Name: tr_contract_file tr_contract_file_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_contract_file
    ADD CONSTRAINT tr_contract_file_pkey PRIMARY KEY (contract_id, file_additional_id);


--
-- Name: tr_contract_parapher tr_contract_parapher_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_contract_parapher
    ADD CONSTRAINT tr_contract_parapher_pkey PRIMARY KEY (contract_id, parapher_id);


--
-- Name: tr_profilmenu tr_profilmenu_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_profilmenu
    ADD CONSTRAINT tr_profilmenu_pkey PRIMARY KEY (pro_id, men_id);


--
-- Name: tr_user_profile tr_user_profile_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_user_profile
    ADD CONSTRAINT tr_user_profile_pkey PRIMARY KEY (user_id, profile_id);


--
-- Name: tr_user_role tr_user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_user_role
    ADD CONSTRAINT tr_user_role_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: traitementmutation_statut traitementmutation_statut_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.traitementmutation_statut
    ADD CONSTRAINT traitementmutation_statut_pkey PRIMARY KEY (traitement_mutation_id);


--
-- Name: traitementpermutation_statut traitementpermutation_statut_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.traitementpermutation_statut
    ADD CONSTRAINT traitementpermutation_statut_pkey PRIMARY KEY (traitement_permutation_id);


--
-- Name: td_besoinenpersonnel_bepfilieredisciplines uk_1cbyq3mtewdgs6c13pqe08ixg; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_besoinenpersonnel_bepfilieredisciplines
    ADD CONSTRAINT uk_1cbyq3mtewdgs6c13pqe08ixg UNIQUE (bepfilieredisciplines_id);


--
-- Name: td_acte_piecejointes uk_1dvw3ky8oo5qd36diyt1hesks; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_acte_piecejointes
    ADD CONSTRAINT uk_1dvw3ky8oo5qd36diyt1hesks UNIQUE (piecejointes_id);


--
-- Name: td_user uk_3pyp4rpl8ix5nqpup2u2e6hsb; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_user
    ADD CONSTRAINT uk_3pyp4rpl8ix5nqpup2u2e6hsb UNIQUE (file_id);


--
-- Name: td_priseencharge_traitementpriseencharges uk_3uw8b3ql3ocwbrof3kop8v1l8; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_priseencharge_traitementpriseencharges
    ADD CONSTRAINT uk_3uw8b3ql3ocwbrof3kop8v1l8 UNIQUE (traitementpriseencharges_traitement_id);


--
-- Name: td_fichesynoptique_classeprofdisciplines uk_556pek416ldqog2qqgryg3sft; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichesynoptique_classeprofdisciplines
    ADD CONSTRAINT uk_556pek416ldqog2qqgryg3sft UNIQUE (classeprofdisciplines_id);


--
-- Name: td_rapport_pv uk_5om28nve4sckfiqb7ojtei7nh; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_rapport_pv
    ADD CONSTRAINT uk_5om28nve4sckfiqb7ojtei7nh UNIQUE (pv_id);


--
-- Name: td_rapport_files uk_67vdhtcxpfb9hbghty4hgq3st; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_rapport_files
    ADD CONSTRAINT uk_67vdhtcxpfb9hbghty4hgq3st UNIQUE (files_id);


--
-- Name: td_contract uk_6psi3yci1mtt598cwwsipp1cv; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_contract
    ADD CONSTRAINT uk_6psi3yci1mtt598cwwsipp1cv UNIQUE (file_id);


--
-- Name: td_dossieragent_actes uk_7runp1gbicblk9jrjxtsrxlnm; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_dossieragent_actes
    ADD CONSTRAINT uk_7runp1gbicblk9jrjxtsrxlnm UNIQUE (actes_acte_id);


--
-- Name: td_user uk_8nukw7uh2t858wibmi3fig4wb; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_user
    ADD CONSTRAINT uk_8nukw7uh2t858wibmi3fig4wb UNIQUE (paraphe_id);


--
-- Name: td_acte_responsabletraitements uk_8t653yallshte4hi9qgvhf3lm; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_acte_responsabletraitements
    ADD CONSTRAINT uk_8t653yallshte4hi9qgvhf3lm UNIQUE (responsabletraitements_id);


--
-- Name: tr_contract_file uk_9p5qv6ab73aimwajx672l5vdh; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_contract_file
    ADD CONSTRAINT uk_9p5qv6ab73aimwajx672l5vdh UNIQUE (file_additional_id);


--
-- Name: tp_emergency_level uk_9vfjg1nuyus15m4wjt8x7hhbk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_emergency_level
    ADD CONSTRAINT uk_9vfjg1nuyus15m4wjt8x7hhbk UNIQUE (code);


--
-- Name: tr_serieclasseprofdiscipline_profdiscipline uk_a0e8c9y450wa78a9kc8d667hx; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_serieclasseprofdiscipline_profdiscipline
    ADD CONSTRAINT uk_a0e8c9y450wa78a9kc8d667hx UNIQUE (profdiscipline_id);


--
-- Name: td_fichiercanditure_files uk_ad0a29y5bb8w83drj8iucdpg5; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichiercanditure_files
    ADD CONSTRAINT uk_ad0a29y5bb8w83drj8iucdpg5 UNIQUE (files_id);


--
-- Name: tr_serieniveaudiscipline_disciplinequantums uk_ad3s08np3e5pik2nsji0r4bre; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_serieniveaudiscipline_disciplinequantums
    ADD CONSTRAINT uk_ad3s08np3e5pik2nsji0r4bre UNIQUE (disciplinequantums_id);


--
-- Name: td_demandestage_justificatfs uk_albamsla2gxubtttgycya3ud5; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_demandestage_justificatfs
    ADD CONSTRAINT uk_albamsla2gxubtttgycya3ud5 UNIQUE (justificatfs_id);


--
-- Name: td_offretechniquefinanciere_files uk_api4ek43ks5p0gmwyamwjakae; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_offretechniquefinanciere_files
    ADD CONSTRAINT uk_api4ek43ks5p0gmwyamwjakae UNIQUE (files_id);


--
-- Name: tp_profile_menus uk_bljusf2tovc31upe95nlxjuoc; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_profile_menus
    ADD CONSTRAINT uk_bljusf2tovc31upe95nlxjuoc UNIQUE (menus_pmsm_id);


--
-- Name: td_dossieragent_avancements uk_c5gse7s0jmwpgr4k43k8b26i1; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_dossieragent_avancements
    ADD CONSTRAINT uk_c5gse7s0jmwpgr4k43k8b26i1 UNIQUE (avancements_avan_id);


--
-- Name: tp_statutdemandestage uk_cadwd3h8ig9b32d0ybfo7fcg; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_statutdemandestage
    ADD CONSTRAINT uk_cadwd3h8ig9b32d0ybfo7fcg UNIQUE (statudeman_code);


--
-- Name: td_fichesynoptique_serieclasseprofdisciplines uk_cgjqhm4jkcqcpbsp0lwpb0o61; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichesynoptique_serieclasseprofdisciplines
    ADD CONSTRAINT uk_cgjqhm4jkcqcpbsp0lwpb0o61 UNIQUE (serieclasseprofdisciplines_id);


--
-- Name: tr_bepfilierediscipline_besoinennombredisciplines uk_e56i8clujiswsrdude2adwb4f; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_bepfilierediscipline_besoinennombredisciplines
    ADD CONSTRAINT uk_e56i8clujiswsrdude2adwb4f UNIQUE (besoinennombredisciplines_id);


--
-- Name: td_besoinenpersonnel_besoinenpersonnelfilieres uk_gp2ikhyjjy34dd1wchguw51yn; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_besoinenpersonnel_besoinenpersonnelfilieres
    ADD CONSTRAINT uk_gp2ikhyjjy34dd1wchguw51yn UNIQUE (besoinenpersonnelfilieres_id);


--
-- Name: td_fichesynoptique_serieniveaudisciplines uk_h802jhkm1vjxx5oa3ffsv33dh; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichesynoptique_serieniveaudisciplines
    ADD CONSTRAINT uk_h802jhkm1vjxx5oa3ffsv33dh UNIQUE (serieniveaudisciplines_id);


--
-- Name: tr_filierediscipline_disciplinequantums uk_hlj1ylm78etd5trejyowmsxdy; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_filierediscipline_disciplinequantums
    ADD CONSTRAINT uk_hlj1ylm78etd5trejyowmsxdy UNIQUE (disciplinequantums_id);


--
-- Name: tp_menu_children uk_hw0j0h1t2fiodaxx17g3w4cw4; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_menu_children
    ADD CONSTRAINT uk_hw0j0h1t2fiodaxx17g3w4cw4 UNIQUE (children_menu_id);


--
-- Name: td_entite uk_i3l94fsw9ytpvk7s4kawjt9i9; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_entite
    ADD CONSTRAINT uk_i3l94fsw9ytpvk7s4kawjt9i9 UNIQUE (name);


--
-- Name: tp_niveauscolaire uk_iu6ldhg7hxf3pc70w2asodjjx; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_niveauscolaire
    ADD CONSTRAINT uk_iu6ldhg7hxf3pc70w2asodjjx UNIQUE (niveau_code);


--
-- Name: td_piecejoint_files uk_jchk5dfu7wdvjmgxd8cn92m6; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_piecejoint_files
    ADD CONSTRAINT uk_jchk5dfu7wdvjmgxd8cn92m6 UNIQUE (files_id);


--
-- Name: td_planformation_files uk_jgwe0cxip4mjf9kptswfybeq3; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_planformation_files
    ADD CONSTRAINT uk_jgwe0cxip4mjf9kptswfybeq3 UNIQUE (files_id);


--
-- Name: td_attestationstage_piecesjoint uk_ji9jyxm9si9xqu72hbo9gnc7o; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_attestationstage_piecesjoint
    ADD CONSTRAINT uk_ji9jyxm9si9xqu72hbo9gnc7o UNIQUE (piecesjoint_id);


--
-- Name: tr_contract_parapher uk_m0do5ixd7e7wvv6k3ifoatlxp; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_contract_parapher
    ADD CONSTRAINT uk_m0do5ixd7e7wvv6k3ifoatlxp UNIQUE (parapher_id);


--
-- Name: td_dossieragent_diplomes uk_n0d5jc0n25dkon47yemg9mq2m; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_dossieragent_diplomes
    ADD CONSTRAINT uk_n0d5jc0n25dkon47yemg9mq2m UNIQUE (diplomes_dip_id);


--
-- Name: td_role uk_nujps4etnl64ivog85kd9bshg; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_role
    ADD CONSTRAINT uk_nujps4etnl64ivog85kd9bshg UNIQUE (name);


--
-- Name: tr_profdiscipline_disciplinequantums uk_nvictwhvkwwv9wpdx6tq5ic24; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_profdiscipline_disciplinequantums
    ADD CONSTRAINT uk_nvictwhvkwwv9wpdx6tq5ic24 UNIQUE (disciplinequantums_id);


--
-- Name: tp_typedemandecourrier uk_oht5r93njxuh6uq4q31k073nc; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_typedemandecourrier
    ADD CONSTRAINT uk_oht5r93njxuh6uq4q31k073nc UNIQUE (typedemcou_code);


--
-- Name: td_disciplinestage uk_ojeh5fq6ktf0xn8k8hsccl3dl; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_disciplinestage
    ADD CONSTRAINT uk_ojeh5fq6ktf0xn8k8hsccl3dl UNIQUE (discipline_code);


--
-- Name: td_account uk_ovdirw0chemwdrhstjmf2gfma; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_account
    ADD CONSTRAINT uk_ovdirw0chemwdrhstjmf2gfma UNIQUE (login);


--
-- Name: td_user uk_oxbwhkj6cqw9xr5rtkbvc27tn; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_user
    ADD CONSTRAINT uk_oxbwhkj6cqw9xr5rtkbvc27tn UNIQUE (email);


--
-- Name: token uk_pddrhgwxnms2aceeku9s2ewy5; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT uk_pddrhgwxnms2aceeku9s2ewy5 UNIQUE (token);


--
-- Name: td_acte_traitementactes uk_pi4kfliurs0a7cpj8l9sqpqj0; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_acte_traitementactes
    ADD CONSTRAINT uk_pi4kfliurs0a7cpj8l9sqpqj0 UNIQUE (traitementactes_traitement_id);


--
-- Name: td_planningformation_files uk_pw9365nuhntpvo9mir4al285g; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_planningformation_files
    ADD CONSTRAINT uk_pw9365nuhntpvo9mir4al285g UNIQUE (files_id);


--
-- Name: td_contract uk_qhxokx9fyplq6m8iwr6nva7p0; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_contract
    ADD CONSTRAINT uk_qhxokx9fyplq6m8iwr6nva7p0 UNIQUE (emergency_level_id);


--
-- Name: td_fichesynoptique_filieredisciplines uk_qk8f6aa64vyd5ceag1a03shdn; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichesynoptique_filieredisciplines
    ADD CONSTRAINT uk_qk8f6aa64vyd5ceag1a03shdn UNIQUE (filieredisciplines_id);


--
-- Name: td_rapportstage_piecesjoint uk_qsdbkmadmpl85kajvyq6ulg22; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_rapportstage_piecesjoint
    ADD CONSTRAINT uk_qsdbkmadmpl85kajvyq6ulg22 UNIQUE (piecesjoint_id);


--
-- Name: td_demandestage_justificatfsauthorisationstage uk_rjpmoqvmp3pit9au7lm67amyt; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_demandestage_justificatfsauthorisationstage
    ADD CONSTRAINT uk_rjpmoqvmp3pit9au7lm67amyt UNIQUE (justificatfsauthorisationstage_id);


--
-- Name: td_dossieragent_etatcivil uk_srqroc018t079i1mhrse482fe; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_dossieragent_etatcivil
    ADD CONSTRAINT uk_srqroc018t079i1mhrse482fe UNIQUE (etatcivil_etat_id);


--
-- Name: td_amendment uk_t0j02ghnw5bw51ngei1jt60dy; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_amendment
    ADD CONSTRAINT uk_t0j02ghnw5bw51ngei1jt60dy UNIQUE (contract_id);


--
-- Name: td_amendment uk_t62vv9qwseuv6ovfb4ptio9yj; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_amendment
    ADD CONSTRAINT uk_t62vv9qwseuv6ovfb4ptio9yj UNIQUE (parapher_id);


--
-- Name: tr_classeprofdiscipline_profdiscipline uk_tkm9pc00unxcn2h9wigwx7vh4; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_classeprofdiscipline_profdiscipline
    ADD CONSTRAINT uk_tkm9pc00unxcn2h9wigwx7vh4 UNIQUE (profdiscipline_id);


--
-- Name: td_priseencharge td_priseencharge_pkey; Type: CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_priseencharge
    ADD CONSTRAINT td_priseencharge_pkey PRIMARY KEY (pec_id);


--
-- Name: td_traitementpriseencharge td_traitementpriseencharge_pkey; Type: CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_traitementpriseencharge
    ADD CONSTRAINT td_traitementpriseencharge_pkey PRIMARY KEY (traitement_id);


--
-- Name: tp_statutpriseencharge tp_statutpriseencharge_pkey; Type: CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.tp_statutpriseencharge
    ADD CONSTRAINT tp_statutpriseencharge_pkey PRIMARY KEY (stat_id);


--
-- Name: tp_typepriseencharge tp_typepriseencharge_pkey; Type: CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.tp_typepriseencharge
    ADD CONSTRAINT tp_typepriseencharge_pkey PRIMARY KEY (typepec_id);


--
-- Name: td_acte td_acte_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT td_acte_pkey PRIMARY KEY (acte_id);


--
-- Name: td_actualite td_actualite_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_actualite
    ADD CONSTRAINT td_actualite_pkey PRIMARY KEY (actu_id);


--
-- Name: td_agent td_agent_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_agent
    ADD CONSTRAINT td_agent_pkey PRIMARY KEY (ag_id);


--
-- Name: td_avancement td_avancement_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_avancement
    ADD CONSTRAINT td_avancement_pkey PRIMARY KEY (avan_id);


--
-- Name: td_besoinenpersonnel td_besoinenpersonnel_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_besoinenpersonnel
    ADD CONSTRAINT td_besoinenpersonnel_pkey PRIMARY KEY (id);


--
-- Name: td_diplome td_diplome_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_diplome
    ADD CONSTRAINT td_diplome_pkey PRIMARY KEY (dip_id);


--
-- Name: td_disciplinequantum td_disciplinequantum_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_disciplinequantum
    ADD CONSTRAINT td_disciplinequantum_pkey PRIMARY KEY (id);


--
-- Name: td_dossieragent td_dossieragent_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_dossieragent
    ADD CONSTRAINT td_dossieragent_pkey PRIMARY KEY (dos_id);


--
-- Name: td_etatcivil td_etatcivil_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_etatcivil
    ADD CONSTRAINT td_etatcivil_pkey PRIMARY KEY (etat_id);


--
-- Name: td_fichesynoptique td_fichesynoptique_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_fichesynoptique
    ADD CONSTRAINT td_fichesynoptique_pkey PRIMARY KEY (id);


--
-- Name: td_imputationoubulletin td_imputationoubulletin_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_imputationoubulletin
    ADD CONSTRAINT td_imputationoubulletin_pkey PRIMARY KEY (imp_id);


--
-- Name: td_mutation td_mutation_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_mutation
    ADD CONSTRAINT td_mutation_pkey PRIMARY KEY (id);


--
-- Name: td_originedemandeurlog td_originedemandeurlog_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_originedemandeurlog
    ADD CONSTRAINT td_originedemandeurlog_pkey PRIMARY KEY (id);


--
-- Name: td_permutation td_permutation_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_permutation
    ADD CONSTRAINT td_permutation_pkey PRIMARY KEY (permu_id);


--
-- Name: td_piecejoint td_piecejoint_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_piecejoint
    ADD CONSTRAINT td_piecejoint_pkey PRIMARY KEY (pj_id);


--
-- Name: td_situation_administrative td_situation_administrative_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_situation_administrative
    ADD CONSTRAINT td_situation_administrative_pkey PRIMARY KEY (sit_id);


--
-- Name: td_traitement_permutation td_traitement_permutation_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_traitement_permutation
    ADD CONSTRAINT td_traitement_permutation_pkey PRIMARY KEY (traite_id);


--
-- Name: td_traitementacte td_traitementacte_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_traitementacte
    ADD CONSTRAINT td_traitementacte_pkey PRIMARY KEY (traitement_id);


--
-- Name: td_traitementmutation td_traitementmutation_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_traitementmutation
    ADD CONSTRAINT td_traitementmutation_pkey PRIMARY KEY (id);


--
-- Name: tp_bordereau tp_bordereau_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_bordereau
    ADD CONSTRAINT tp_bordereau_pkey PRIMARY KEY (bord_id);


--
-- Name: tp_categorieactualite tp_categorieactualite_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_categorieactualite
    ADD CONSTRAINT tp_categorieactualite_pkey PRIMARY KEY (cat_actu_id);


--
-- Name: tp_classe tp_classe_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_classe
    ADD CONSTRAINT tp_classe_pkey PRIMARY KEY (id);


--
-- Name: tp_diplome_list tp_diplome_list_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_diplome_list
    ADD CONSTRAINT tp_diplome_list_pkey PRIMARY KEY (dip_id);


--
-- Name: tp_discipline tp_discipline_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_discipline
    ADD CONSTRAINT tp_discipline_pkey PRIMARY KEY (id);


--
-- Name: tp_filiere tp_filiere_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_filiere
    ADD CONSTRAINT tp_filiere_pkey PRIMARY KEY (id);


--
-- Name: tp_formation_prof tp_formation_prof_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_formation_prof
    ADD CONSTRAINT tp_formation_prof_pkey PRIMARY KEY (id);


--
-- Name: tp_niveau tp_niveau_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_niveau
    ADD CONSTRAINT tp_niveau_pkey PRIMARY KEY (id);


--
-- Name: tp_serie tp_serie_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_serie
    ADD CONSTRAINT tp_serie_pkey PRIMARY KEY (id);


--
-- Name: tp_status_permutation tp_status_permutation_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_status_permutation
    ADD CONSTRAINT tp_status_permutation_pkey PRIMARY KEY (status_permutation_id);


--
-- Name: tp_statutacte tp_statutacte_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_statutacte
    ADD CONSTRAINT tp_statutacte_pkey PRIMARY KEY (stat_id);


--
-- Name: tp_statutbesoinenpersonnel tp_statutbesoinenpersonnel_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_statutbesoinenpersonnel
    ADD CONSTRAINT tp_statutbesoinenpersonnel_pkey PRIMARY KEY (id);


--
-- Name: tp_statutmutation tp_statutmutation_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_statutmutation
    ADD CONSTRAINT tp_statutmutation_pkey PRIMARY KEY (id);


--
-- Name: tp_statuttraitementacte tp_statuttraitementacte_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_statuttraitementacte
    ADD CONSTRAINT tp_statuttraitementacte_pkey PRIMARY KEY (stattraitement_id);


--
-- Name: tp_type_article tp_type_article_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_type_article
    ADD CONSTRAINT tp_type_article_pkey PRIMARY KEY (type_art_id);


--
-- Name: tp_typeaa tp_typeaa_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_typeaa
    ADD CONSTRAINT tp_typeaa_pkey PRIMARY KEY (typeaa_id);


--
-- Name: tp_typeacte tp_typeacte_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_typeacte
    ADD CONSTRAINT tp_typeacte_pkey PRIMARY KEY (type_id);


--
-- Name: tp_typeag tp_typeag_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_typeag
    ADD CONSTRAINT tp_typeag_pkey PRIMARY KEY (typeag_id);


--
-- Name: tr_bepfilierediscipline tr_bepfilierediscipline_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_bepfilierediscipline
    ADD CONSTRAINT tr_bepfilierediscipline_pkey PRIMARY KEY (id);


--
-- Name: tr_besoinennombrediscipline tr_besoinennombrediscipline_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_besoinennombrediscipline
    ADD CONSTRAINT tr_besoinennombrediscipline_pkey PRIMARY KEY (id);


--
-- Name: tr_classeprofdiscipline tr_classeprofdiscipline_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_classeprofdiscipline
    ADD CONSTRAINT tr_classeprofdiscipline_pkey PRIMARY KEY (id);


--
-- Name: tr_filierediscipline tr_filierediscipline_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_filierediscipline
    ADD CONSTRAINT tr_filierediscipline_pkey PRIMARY KEY (id);


--
-- Name: tr_profdiscipline tr_profdiscipline_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_profdiscipline
    ADD CONSTRAINT tr_profdiscipline_pkey PRIMARY KEY (id);


--
-- Name: tr_serieclasseprofdiscipline tr_serieclasseprofdiscipline_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_serieclasseprofdiscipline
    ADD CONSTRAINT tr_serieclasseprofdiscipline_pkey PRIMARY KEY (id);


--
-- Name: tr_serieniveaudiscipline tr_serieniveaudiscipline_pkey; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_serieniveaudiscipline
    ADD CONSTRAINT tr_serieniveaudiscipline_pkey PRIMARY KEY (id);


--
-- Name: td_acte uk_1vdcnj8w753xpqajr3ig61em9; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT uk_1vdcnj8w753xpqajr3ig61em9 UNIQUE (predbordereau_bord_id);


--
-- Name: td_diplome uk_4eeq8ulihajaug3nfs0qlf9i1; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_diplome
    ADD CONSTRAINT uk_4eeq8ulihajaug3nfs0qlf9i1 UNIQUE (piecejointes_id);


--
-- Name: td_etatcivil uk_7k6oqwmaj7wyqr58l6evoon90; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_etatcivil
    ADD CONSTRAINT uk_7k6oqwmaj7wyqr58l6evoon90 UNIQUE (piecejointes_id);


--
-- Name: td_fichesynoptique uk_9rkhadnd39mxnihctwa29383y; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_fichesynoptique
    ADD CONSTRAINT uk_9rkhadnd39mxnihctwa29383y UNIQUE (deconcentredlevelid);


--
-- Name: td_acte uk_bt4mgnh81l6xvsl7t1ny5ckx6; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT uk_bt4mgnh81l6xvsl7t1ny5ckx6 UNIQUE (currentbordereau_bord_id);


--
-- Name: td_situation_administrative uk_db02swng4prxvik126g4pno6j; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_situation_administrative
    ADD CONSTRAINT uk_db02swng4prxvik126g4pno6j UNIQUE (piecejointes_id);


--
-- Name: td_traitement_permutation uk_dim29uh3yv66ya4r8fbos3bf9; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_traitement_permutation
    ADD CONSTRAINT uk_dim29uh3yv66ya4r8fbos3bf9 UNIQUE (bordereauvalidation_id);


--
-- Name: td_dossieragent uk_ju4sm50oxtxi2wcogogjh3q0g; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_dossieragent
    ADD CONSTRAINT uk_ju4sm50oxtxi2wcogogjh3q0g UNIQUE (userid);


--
-- Name: tp_bordereau uk_kyhx6sf7812u8a2kyfejlmxt4; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_bordereau
    ADD CONSTRAINT uk_kyhx6sf7812u8a2kyfejlmxt4 UNIQUE (utilisateur_id);


--
-- Name: td_agent uk_nu3d90vl09tws1all7bx56qvv; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_agent
    ADD CONSTRAINT uk_nu3d90vl09tws1all7bx56qvv UNIQUE (deconcentredlevelid);


--
-- Name: td_permutation uk_o4f3cjrblnia9wjiy2d9j1ui9; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_permutation
    ADD CONSTRAINT uk_o4f3cjrblnia9wjiy2d9j1ui9 UNIQUE (traitementpermutation_traite_id);


--
-- Name: td_avancement uk_rdhina2561i1fhqtr73st0h7r; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_avancement
    ADD CONSTRAINT uk_rdhina2561i1fhqtr73st0h7r UNIQUE (piecejointes_id);


--
-- Name: td_agent uk_s1s38b4mudh4bjoi0t0ihendv; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_agent
    ADD CONSTRAINT uk_s1s38b4mudh4bjoi0t0ihendv UNIQUE (dosid);


--
-- Name: td_mutation uk_s7pdtexipln333b5slwdcw0un; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_mutation
    ADD CONSTRAINT uk_s7pdtexipln333b5slwdcw0un UNIQUE (originedemandeurlog_id);


--
-- Name: td_mutation uk_si6l2j9794bl0t78nihncia7g; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_mutation
    ADD CONSTRAINT uk_si6l2j9794bl0t78nihncia7g UNIQUE (traitementmutation_id);


--
-- Name: td_actualite uk_ti35pbvw2y9swq24hg0hhfpgl; Type: CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_actualite
    ADD CONSTRAINT uk_ti35pbvw2y9swq24hg0hhfpgl UNIQUE (image_id);


--
-- Name: td_attestationstage td_attestationstage_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_attestationstage
    ADD CONSTRAINT td_attestationstage_pkey PRIMARY KEY (attestation_id);


--
-- Name: td_avisdemandestage td_avisdemandestage_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_avisdemandestage
    ADD CONSTRAINT td_avisdemandestage_pkey PRIMARY KEY (avis_id);


--
-- Name: td_campagne td_campagne_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_campagne
    ADD CONSTRAINT td_campagne_pkey PRIMARY KEY (cam_id);


--
-- Name: td_convocation td_convocation_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_convocation
    ADD CONSTRAINT td_convocation_pkey PRIMARY KEY (id);


--
-- Name: td_courrier td_courrier_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_courrier
    ADD CONSTRAINT td_courrier_pkey PRIMARY KEY (courrier_id);


--
-- Name: td_demandestage td_demandestage_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_demandestage
    ADD CONSTRAINT td_demandestage_pkey PRIMARY KEY (demande_id);


--
-- Name: td_disciplinestage td_disciplinestage_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_disciplinestage
    ADD CONSTRAINT td_disciplinestage_pkey PRIMARY KEY (discipline_id);


--
-- Name: td_expressiondebesoin td_expressiondebesoin_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_expressiondebesoin
    ADD CONSTRAINT td_expressiondebesoin_pkey PRIMARY KEY (exp_id);


--
-- Name: td_fichiercanditure td_fichiercanditure_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_fichiercanditure
    ADD CONSTRAINT td_fichiercanditure_pkey PRIMARY KEY (id);


--
-- Name: td_formation td_formation_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_formation
    ADD CONSTRAINT td_formation_pkey PRIMARY KEY (id);


--
-- Name: td_offretechniquefinanciere td_offretechniquefinanciere_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_offretechniquefinanciere
    ADD CONSTRAINT td_offretechniquefinanciere_pkey PRIMARY KEY (id);


--
-- Name: td_participant_definitif td_participant_definitif_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_participant_definitif
    ADD CONSTRAINT td_participant_definitif_pkey PRIMARY KEY (id);


--
-- Name: td_participant td_participant_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_participant
    ADD CONSTRAINT td_participant_pkey PRIMARY KEY (id);


--
-- Name: td_participation td_participation_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_participation
    ADD CONSTRAINT td_participation_pkey PRIMARY KEY (id);


--
-- Name: td_planformation td_planformation_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_planformation
    ADD CONSTRAINT td_planformation_pkey PRIMARY KEY (id);


--
-- Name: td_planningformation td_planningformation_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_planningformation
    ADD CONSTRAINT td_planningformation_pkey PRIMARY KEY (id);


--
-- Name: td_pvexamen td_pvexamen_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_pvexamen
    ADD CONSTRAINT td_pvexamen_pkey PRIMARY KEY (id);


--
-- Name: td_rapport td_rapport_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_rapport
    ADD CONSTRAINT td_rapport_pkey PRIMARY KEY (id);


--
-- Name: td_rapportstage td_rapportstage_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_rapportstage
    ADD CONSTRAINT td_rapportstage_pkey PRIMARY KEY (rapport_id);


--
-- Name: td_session td_session_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_session
    ADD CONSTRAINT td_session_pkey PRIMARY KEY (id);


--
-- Name: td_statut_formation td_statut_formation_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_statut_formation
    ADD CONSTRAINT td_statut_formation_pkey PRIMARY KEY (id);


--
-- Name: td_statut_offre_technique td_statut_offre_technique_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_statut_offre_technique
    ADD CONSTRAINT td_statut_offre_technique_pkey PRIMARY KEY (id);


--
-- Name: td_statut_planformation td_statut_planformation_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_statut_planformation
    ADD CONSTRAINT td_statut_planformation_pkey PRIMARY KEY (id);


--
-- Name: td_tableau_suivi td_tableau_suivi_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_tableau_suivi
    ADD CONSTRAINT td_tableau_suivi_pkey PRIMARY KEY (id);


--
-- Name: td_themeformation td_themeformation_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_themeformation
    ADD CONSTRAINT td_themeformation_pkey PRIMARY KEY (id);


--
-- Name: td_traitementcampagne td_traitementcampagne_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_traitementcampagne
    ADD CONSTRAINT td_traitementcampagne_pkey PRIMARY KEY (traitcam_id);


--
-- Name: td_traitementcourrier td_traitementcourrier_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_traitementcourrier
    ADD CONSTRAINT td_traitementcourrier_pkey PRIMARY KEY (traitcou_id);


--
-- Name: td_traitementdemandestage td_traitementdemandestage_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_traitementdemandestage
    ADD CONSTRAINT td_traitementdemandestage_pkey PRIMARY KEY (traitdeman_id);


--
-- Name: td_traitementexpressiondebesoin td_traitementexpressiondebesoin_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_traitementexpressiondebesoin
    ADD CONSTRAINT td_traitementexpressiondebesoin_pkey PRIMARY KEY (traitexp_id);


--
-- Name: td_type_formation td_type_formation_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_type_formation
    ADD CONSTRAINT td_type_formation_pkey PRIMARY KEY (id);


--
-- Name: tp_niveauscolaire tp_niveauscolaire_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tp_niveauscolaire
    ADD CONSTRAINT tp_niveauscolaire_pkey PRIMARY KEY (niveau_id);


--
-- Name: tp_statutcampagne tp_statutcampagne_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tp_statutcampagne
    ADD CONSTRAINT tp_statutcampagne_pkey PRIMARY KEY (stcam_id);


--
-- Name: tp_statutcourrier tp_statutcourrier_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tp_statutcourrier
    ADD CONSTRAINT tp_statutcourrier_pkey PRIMARY KEY (stcou_id);


--
-- Name: tp_statutdemandestage tp_statutdemandestage_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tp_statutdemandestage
    ADD CONSTRAINT tp_statutdemandestage_pkey PRIMARY KEY (statudeman_id);


--
-- Name: tp_statutexpressiondebesoin tp_statutexpressiondebesoin_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tp_statutexpressiondebesoin
    ADD CONSTRAINT tp_statutexpressiondebesoin_pkey PRIMARY KEY (stexp_id);


--
-- Name: tp_typedemandecourrier tp_typedemandecourrier_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tp_typedemandecourrier
    ADD CONSTRAINT tp_typedemandecourrier_pkey PRIMARY KEY (typedemcou_id);


--
-- Name: tr_theme_formation_profile tr_theme_formation_profile_pkey; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tr_theme_formation_profile
    ADD CONSTRAINT tr_theme_formation_profile_pkey PRIMARY KEY (theme_id, profile_id);


--
-- Name: td_session uk_3ivd8tj8j12k3kky9quj2vuwo; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_session
    ADD CONSTRAINT uk_3ivd8tj8j12k3kky9quj2vuwo UNIQUE (rapport_file_id);


--
-- Name: td_rapportstage uk_49onydjihtiari73km4elamxe; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_rapportstage
    ADD CONSTRAINT uk_49onydjihtiari73km4elamxe UNIQUE (rapport_demandestage);


--
-- Name: td_demandestage uk_5wo9xbl14hpl9rq3cegd4rjv8; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_demandestage
    ADD CONSTRAINT uk_5wo9xbl14hpl9rq3cegd4rjv8 UNIQUE (demande_numero);


--
-- Name: td_attestationstage uk_69daev3yx5rxhf4ei6g4laafl; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_attestationstage
    ADD CONSTRAINT uk_69daev3yx5rxhf4ei6g4laafl UNIQUE (attestation_demandestage);


--
-- Name: tp_statutexpressiondebesoin uk_7cwnigoohjl2h5tyhwask661w; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tp_statutexpressiondebesoin
    ADD CONSTRAINT uk_7cwnigoohjl2h5tyhwask661w UNIQUE (stexp_code);


--
-- Name: tp_statutcampagne uk_7iogm5g0tc882fo8vdaocorkh; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tp_statutcampagne
    ADD CONSTRAINT uk_7iogm5g0tc882fo8vdaocorkh UNIQUE (stcam_code);


--
-- Name: td_type_formation uk_8rmdce4wojqww12ge46u414nb; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_type_formation
    ADD CONSTRAINT uk_8rmdce4wojqww12ge46u414nb UNIQUE (code);


--
-- Name: td_participant uk_9ds2j9bth9ha1eqky0ka1xshs; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_participant
    ADD CONSTRAINT uk_9ds2j9bth9ha1eqky0ka1xshs UNIQUE (participant_id);


--
-- Name: td_convocation uk_alt0aecl94cexud534hvda0pq; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_convocation
    ADD CONSTRAINT uk_alt0aecl94cexud534hvda0pq UNIQUE (tdr_id);


--
-- Name: td_rapport uk_bftncyruxxm1egc2npavnj3gy; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_rapport
    ADD CONSTRAINT uk_bftncyruxxm1egc2npavnj3gy UNIQUE (formation_id);


--
-- Name: td_rapportstage uk_bqxsc5ycfgqycji7m5ll8xksn; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_rapportstage
    ADD CONSTRAINT uk_bqxsc5ycfgqycji7m5ll8xksn UNIQUE (piecesjoint_id);


--
-- Name: tp_statutdemandestage uk_cadwd3h8ig9b32d0ybfo7fcg; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tp_statutdemandestage
    ADD CONSTRAINT uk_cadwd3h8ig9b32d0ybfo7fcg UNIQUE (statudeman_code);


--
-- Name: td_statut_planformation uk_cjo5i54yjq9qepu1ejpgbyfa6; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_statut_planformation
    ADD CONSTRAINT uk_cjo5i54yjq9qepu1ejpgbyfa6 UNIQUE (code);


--
-- Name: td_participant uk_e21bb30wyw7v3r0vf66dl5699; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_participant
    ADD CONSTRAINT uk_e21bb30wyw7v3r0vf66dl5699 UNIQUE (formation_id);


--
-- Name: td_statut_offre_technique uk_ekrwt2fpeh4ijv7w01m9mue4; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_statut_offre_technique
    ADD CONSTRAINT uk_ekrwt2fpeh4ijv7w01m9mue4 UNIQUE (libelle);


--
-- Name: td_attestationstage uk_fw565pioijfi5ierbqemmgjd0; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_attestationstage
    ADD CONSTRAINT uk_fw565pioijfi5ierbqemmgjd0 UNIQUE (piecesjoint_id);


--
-- Name: td_courrier uk_gxs1eqyy25o5r278e4g3xprpw; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_courrier
    ADD CONSTRAINT uk_gxs1eqyy25o5r278e4g3xprpw UNIQUE (courrier_reference);


--
-- Name: td_statut_planformation uk_ids2pv3csbw6p7dja81tjgvol; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_statut_planformation
    ADD CONSTRAINT uk_ids2pv3csbw6p7dja81tjgvol UNIQUE (libelle);


--
-- Name: tp_niveauscolaire uk_iu6ldhg7hxf3pc70w2asodjjx; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tp_niveauscolaire
    ADD CONSTRAINT uk_iu6ldhg7hxf3pc70w2asodjjx UNIQUE (niveau_code);


--
-- Name: td_disciplinestage uk_j376ien4l0yipdw58js85xkwk; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_disciplinestage
    ADD CONSTRAINT uk_j376ien4l0yipdw58js85xkwk UNIQUE (discipline_code);


--
-- Name: td_fichiercanditure uk_j3r6d70g8uq9785nm6272lqah; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_fichiercanditure
    ADD CONSTRAINT uk_j3r6d70g8uq9785nm6272lqah UNIQUE (chefeff_id);


--
-- Name: td_statut_offre_technique uk_jr4954oxl6h7e6257a4l4m05b; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_statut_offre_technique
    ADD CONSTRAINT uk_jr4954oxl6h7e6257a4l4m05b UNIQUE (code);


--
-- Name: td_statut_formation uk_kmnw6r8tyqf96mcv5u7845nmn; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_statut_formation
    ADD CONSTRAINT uk_kmnw6r8tyqf96mcv5u7845nmn UNIQUE (code);


--
-- Name: td_formation uk_kslutqfv11egkufdackkik5d5; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_formation
    ADD CONSTRAINT uk_kslutqfv11egkufdackkik5d5 UNIQUE (cahier_charge_id);


--
-- Name: td_type_formation uk_l0gjmjp4j7y3tle6dagtofh0r; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_type_formation
    ADD CONSTRAINT uk_l0gjmjp4j7y3tle6dagtofh0r UNIQUE (libelle);


--
-- Name: td_statut_formation uk_lbhdliv7ncqd0unoyiv4y7hrt; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_statut_formation
    ADD CONSTRAINT uk_lbhdliv7ncqd0unoyiv4y7hrt UNIQUE (libelle);


--
-- Name: tp_statutcourrier uk_lxrulmwstge35klmn5gpn036w; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tp_statutcourrier
    ADD CONSTRAINT uk_lxrulmwstge35klmn5gpn036w UNIQUE (stcou_code);


--
-- Name: tp_typedemandecourrier uk_oht5r93njxuh6uq4q31k073nc; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tp_typedemandecourrier
    ADD CONSTRAINT uk_oht5r93njxuh6uq4q31k073nc UNIQUE (typedemcou_code);


--
-- Name: td_pvexamen uk_q58gyi6mue3okxmvckjh734je; Type: CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_pvexamen
    ADD CONSTRAINT uk_q58gyi6mue3okxmvckjh734je UNIQUE (rapport_file_id);


--
-- Name: td_actionpta td_actionpta_pkey; Type: CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_actionpta
    ADD CONSTRAINT td_actionpta_pkey PRIMARY KEY (id);


--
-- Name: td_initialpta td_initialpta_pkey; Type: CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_initialpta
    ADD CONSTRAINT td_initialpta_pkey PRIMARY KEY (id);


--
-- Name: td_modecalcul td_modecalcul_pkey; Type: CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_modecalcul
    ADD CONSTRAINT td_modecalcul_pkey PRIMARY KEY (id);


--
-- Name: td_parametre td_parametre_pkey; Type: CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_parametre
    ADD CONSTRAINT td_parametre_pkey PRIMARY KEY (param_id);


--
-- Name: td_plandetravail td_plandetravail_pkey; Type: CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_plandetravail
    ADD CONSTRAINT td_plandetravail_pkey PRIMARY KEY (id);


--
-- Name: td_reportrealisation td_reportrealisation_pkey; Type: CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_reportrealisation
    ADD CONSTRAINT td_reportrealisation_pkey PRIMARY KEY (id);


--
-- Name: td_resultatpta td_resultatpta_pkey; Type: CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_resultatpta
    ADD CONSTRAINT td_resultatpta_pkey PRIMARY KEY (id);


--
-- Name: td_subaction td_subaction_pkey; Type: CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_subaction
    ADD CONSTRAINT td_subaction_pkey PRIMARY KEY (id);


--
-- Name: td_tdsequence td_tdsequence_pkey; Type: CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_tdsequence
    ADD CONSTRAINT td_tdsequence_pkey PRIMARY KEY (id);


--
-- Name: td_traitementparametre td_traitementparametre_pkey; Type: CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_traitementparametre
    ADD CONSTRAINT td_traitementparametre_pkey PRIMARY KEY (traitparam_id);


--
-- Name: tp_statutparametre tp_statutparametre_pkey; Type: CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.tp_statutparametre
    ADD CONSTRAINT tp_statutparametre_pkey PRIMARY KEY (stuparam_id);


--
-- Name: tp_statutparametre uk_fwmyyefpp2hdr89bn2d10efqo; Type: CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.tp_statutparametre
    ADD CONSTRAINT uk_fwmyyefpp2hdr89bn2d10efqo UNIQUE (stuparam_code);


--
-- Name: td_parametre uk_okwv55ulqpcrir4h64jll7pun; Type: CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_parametre
    ADD CONSTRAINT uk_okwv55ulqpcrir4h64jll7pun UNIQUE (param_numero);


--
-- Name: notification_readers notification_readers_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.notification_readers
    ADD CONSTRAINT notification_readers_pkey PRIMARY KEY (notification_id, user_id);


--
-- Name: td_archived_utilisateur td_archived_utilisateur_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT td_archived_utilisateur_pkey PRIMARY KEY (id);


--
-- Name: td_centrallevel td_centrallevel_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT td_centrallevel_pkey PRIMARY KEY (id);


--
-- Name: td_contacts td_contacts_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_contacts
    ADD CONSTRAINT td_contacts_pkey PRIMARY KEY (contact_id);


--
-- Name: td_deconcentratedlevel td_deconcentratedlevel_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT td_deconcentratedlevel_pkey PRIMARY KEY (id);


--
-- Name: td_diplome td_diplome_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_diplome
    ADD CONSTRAINT td_diplome_pkey PRIMARY KEY (dip_id);


--
-- Name: td_failedmail td_failedmail_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_failedmail
    ADD CONSTRAINT td_failedmail_pkey PRIMARY KEY (fai_id);


--
-- Name: td_file td_file_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_file
    ADD CONSTRAINT td_file_pkey PRIMARY KEY (id);


--
-- Name: td_notification td_notification_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_notification
    ADD CONSTRAINT td_notification_pkey PRIMARY KEY (id);


--
-- Name: td_parametre_corpsgrade_pk td_parametre_corpsgrade_pk_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_parametre_corpsgrade_pk
    ADD CONSTRAINT td_parametre_corpsgrade_pk_pkey PRIMARY KEY (params_cg_id);


--
-- Name: td_parametre_corpsgrade td_parametre_corpsgrade_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_parametre_corpsgrade
    ADD CONSTRAINT td_parametre_corpsgrade_pkey PRIMARY KEY (params_cg_id);


--
-- Name: td_tdsequence_ref td_tdsequence_ref_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_tdsequence_ref
    ADD CONSTRAINT td_tdsequence_ref_pkey PRIMARY KEY (id);


--
-- Name: td_usermanager td_usermanager_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_usermanager
    ADD CONSTRAINT td_usermanager_pkey PRIMARY KEY (id);


--
-- Name: tp_bureau tp_bureau_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_bureau
    ADD CONSTRAINT tp_bureau_pkey PRIMARY KEY (bur_id);


--
-- Name: tp_cfp tp_cfp_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_cfp
    ADD CONSTRAINT tp_cfp_pkey PRIMARY KEY (cfp_id);


--
-- Name: tp_corps tp_corps_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_corps
    ADD CONSTRAINT tp_corps_pkey PRIMARY KEY (cor_id);


--
-- Name: tp_diplome_aca tp_diplome_aca_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_diplome_aca
    ADD CONSTRAINT tp_diplome_aca_pkey PRIMARY KEY (dip_aca_id);


--
-- Name: tp_diplome_ped tp_diplome_ped_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_diplome_ped
    ADD CONSTRAINT tp_diplome_ped_pkey PRIMARY KEY (dip_ped_id);


--
-- Name: tp_diplome_prof tp_diplome_prof_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_diplome_prof
    ADD CONSTRAINT tp_diplome_prof_pkey PRIMARY KEY (dip_prof_id);


--
-- Name: tp_direction tp_direction_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_direction
    ADD CONSTRAINT tp_direction_pkey PRIMARY KEY (dir_id);


--
-- Name: tp_division tp_division_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_division
    ADD CONSTRAINT tp_division_pkey PRIMARY KEY (div_id);


--
-- Name: tp_eef tp_eef_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_eef
    ADD CONSTRAINT tp_eef_pkey PRIMARY KEY (eef_id);


--
-- Name: tp_eefministere tp_eefministere_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_eefministere
    ADD CONSTRAINT tp_eefministere_pkey PRIMARY KEY (eefmi_id);


--
-- Name: tp_efspeciality tp_efspeciality_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_efspeciality
    ADD CONSTRAINT tp_efspeciality_pkey PRIMARY KEY (eef_spec_id);


--
-- Name: tp_entity tp_entity_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_entity
    ADD CONSTRAINT tp_entity_pkey PRIMARY KEY (ent_id);


--
-- Name: tp_etablissement_type tp_etablissement_type_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_etablissement_type
    ADD CONSTRAINT tp_etablissement_type_pkey PRIMARY KEY (eta_id);


--
-- Name: tp_fonction tp_fonction_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_fonction
    ADD CONSTRAINT tp_fonction_pkey PRIMARY KEY (fon_id);


--
-- Name: tp_grade tp_grade_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_grade
    ADD CONSTRAINT tp_grade_pkey PRIMARY KEY (grade_id);


--
-- Name: tp_ia tp_ia_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_ia
    ADD CONSTRAINT tp_ia_pkey PRIMARY KEY (ia_id);


--
-- Name: tp_ief tp_ief_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_ief
    ADD CONSTRAINT tp_ief_pkey PRIMARY KEY (seq_ief);


--
-- Name: tp_menu tp_menu_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_menu
    ADD CONSTRAINT tp_menu_pkey PRIMARY KEY (menu_id);


--
-- Name: tp_profile_menu_child tp_profile_menu_child_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_profile_menu_child
    ADD CONSTRAINT tp_profile_menu_child_pkey PRIMARY KEY (pmc_id);


--
-- Name: tp_profile tp_profile_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_profile
    ADD CONSTRAINT tp_profile_pkey PRIMARY KEY (pro_id);


--
-- Name: tp_region tp_region_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_region
    ADD CONSTRAINT tp_region_pkey PRIMARY KEY (reg_id);


--
-- Name: tp_service tp_service_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_service
    ADD CONSTRAINT tp_service_pkey PRIMARY KEY (ser_id);


--
-- Name: tp_specialiteeef tp_specialiteeef_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_specialiteeef
    ADD CONSTRAINT tp_specialiteeef_pkey PRIMARY KEY (speeef_id);


--
-- Name: tp_speciality tp_speciality_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_speciality
    ADD CONSTRAINT tp_speciality_pkey PRIMARY KEY (spe_id);


--
-- Name: tp_structure_mfpaa tp_structure_mfpaa_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_structure_mfpaa
    ADD CONSTRAINT tp_structure_mfpaa_pkey PRIMARY KEY (smfpa_id);


--
-- Name: tp_structure tp_structure_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_structure
    ADD CONSTRAINT tp_structure_pkey PRIMARY KEY (str_id);


--
-- Name: tp_type_diplome tp_type_diplome_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_type_diplome
    ADD CONSTRAINT tp_type_diplome_pkey PRIMARY KEY (type_dip_id);


--
-- Name: tp_type_matricule tp_type_matricule_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_type_matricule
    ADD CONSTRAINT tp_type_matricule_pkey PRIMARY KEY (type_mat_id);


--
-- Name: tp_type_poste tp_type_poste_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_type_poste
    ADD CONSTRAINT tp_type_poste_pkey PRIMARY KEY (type_poste_id);


--
-- Name: tp_type_systeme_enseignement tp_type_systeme_enseignement_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_type_systeme_enseignement
    ADD CONSTRAINT tp_type_systeme_enseignement_pkey PRIMARY KEY (typesystemeens_id);


--
-- Name: tp_typeetablissement tp_typeetablissement_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_typeetablissement
    ADD CONSTRAINT tp_typeetablissement_pkey PRIMARY KEY (eta_id);


--
-- Name: tp_typesysteme_enseignement tp_typesysteme_enseignement_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_typesysteme_enseignement
    ADD CONSTRAINT tp_typesysteme_enseignement_pkey PRIMARY KEY (typesystemeens_id);


--
-- Name: tp_typesystemeenseignement tp_typesystemeenseignement_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_typesystemeenseignement
    ADD CONSTRAINT tp_typesystemeenseignement_pkey PRIMARY KEY (typesystemeens_id);


--
-- Name: tr_men_sous_menu tr_men_sous_menu_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tr_men_sous_menu
    ADD CONSTRAINT tr_men_sous_menu_pkey PRIMARY KEY (men_id, smn_id);


--
-- Name: tr_profilemenusousmenu tr_profilemenusousmenu_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tr_profilemenusousmenu
    ADD CONSTRAINT tr_profilemenusousmenu_pkey PRIMARY KEY (pmsm_id);


--
-- Name: tr_profilmenu tr_profilmenu_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tr_profilmenu
    ADD CONSTRAINT tr_profilmenu_pkey PRIMARY KEY (pro_id, men_id);


--
-- Name: tr_speciality_etablissement tr_speciality_etablissement_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tr_speciality_etablissement
    ADD CONSTRAINT tr_speciality_etablissement_pkey PRIMARY KEY (spec_etab_id);


--
-- Name: tr_user_profile tr_user_profile_pkey; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tr_user_profile
    ADD CONSTRAINT tr_user_profile_pkey PRIMARY KEY (user_id, profile_id);


--
-- Name: tp_diplome_aca uk_27enbwjng1fbilpkvvyh7ci7y; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_diplome_aca
    ADD CONSTRAINT uk_27enbwjng1fbilpkvvyh7ci7y UNIQUE (dip_aca_code);


--
-- Name: tp_bureau uk_2ak6eapwgx4qqm23jy18h27yk; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_bureau
    ADD CONSTRAINT uk_2ak6eapwgx4qqm23jy18h27yk UNIQUE (bur_code);


--
-- Name: tp_direction uk_34q5rx2nni7cl1i4bvc21m9xm; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_direction
    ADD CONSTRAINT uk_34q5rx2nni7cl1i4bvc21m9xm UNIQUE (dir_code);


--
-- Name: tp_type_diplome uk_5up8i9a9qkkawwicfdm0e9gg6; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_type_diplome
    ADD CONSTRAINT uk_5up8i9a9qkkawwicfdm0e9gg6 UNIQUE (type_dip_code);


--
-- Name: td_diplome uk_894e6agdjsp8kqgebyrrtin9v; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_diplome
    ADD CONSTRAINT uk_894e6agdjsp8kqgebyrrtin9v UNIQUE (dip_code);


--
-- Name: tp_region uk_af51er1ptwyejie2guv7tnbrm; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_region
    ADD CONSTRAINT uk_af51er1ptwyejie2guv7tnbrm UNIQUE (reg_code);


--
-- Name: tp_grade uk_cuwisxkdra26x8291nuuyf62i; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_grade
    ADD CONSTRAINT uk_cuwisxkdra26x8291nuuyf62i UNIQUE (grade_code);


--
-- Name: tp_corps uk_fna8ai42qp09nbgk2kf0mh645; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_corps
    ADD CONSTRAINT uk_fna8ai42qp09nbgk2kf0mh645 UNIQUE (cor_code);


--
-- Name: tp_diplome_prof uk_iiqixriyqcsdwyigvcdgo7y36; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_diplome_prof
    ADD CONSTRAINT uk_iiqixriyqcsdwyigvcdgo7y36 UNIQUE (dip_prof_code);


--
-- Name: tp_type_poste uk_jjkygx9ow6ur83dous98oyo4r; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_type_poste
    ADD CONSTRAINT uk_jjkygx9ow6ur83dous98oyo4r UNIQUE (type_poste_code);


--
-- Name: tp_diplome_ped uk_jolkw0qmewhxg1l4caycwf303; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_diplome_ped
    ADD CONSTRAINT uk_jolkw0qmewhxg1l4caycwf303 UNIQUE (dip_ped_code);


--
-- Name: tp_type_matricule uk_k5m2c18053b302ng4oxgmt6t7; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_type_matricule
    ADD CONSTRAINT uk_k5m2c18053b302ng4oxgmt6t7 UNIQUE (type_mat_code);


--
-- Name: tp_service uk_k912vhc4qhxpuab7hwhmq3ng6; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_service
    ADD CONSTRAINT uk_k912vhc4qhxpuab7hwhmq3ng6 UNIQUE (ser_code);


--
-- Name: tp_division uk_q7w0w9t3gr7rehsv0ls8tfn0r; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_division
    ADD CONSTRAINT uk_q7w0w9t3gr7rehsv0ls8tfn0r UNIQUE (div_code);


--
-- Name: tp_fonction uk_rylfttnam1ix7awimeh3ue7rn; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_fonction
    ADD CONSTRAINT uk_rylfttnam1ix7awimeh3ue7rn UNIQUE (pro_code);


--
-- Name: tp_profile uk_s2kcvg8p7iw4xqirjl97yojfo; Type: CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_profile
    ADD CONSTRAINT uk_s2kcvg8p7iw4xqirjl97yojfo UNIQUE (pro_code);


--
-- Name: td_contract fk176dxhraddu2uypyt2px8729l; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_contract
    ADD CONSTRAINT fk176dxhraddu2uypyt2px8729l FOREIGN KEY (emergency_level_id) REFERENCES public.tp_emergency_level(id);


--
-- Name: traitementmutation_statut fk1a64asw5mk3rc45wifok6k6cd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.traitementmutation_statut
    ADD CONSTRAINT fk1a64asw5mk3rc45wifok6k6cd FOREIGN KEY (statutmut_id) REFERENCES schema_carriere.tp_statutmutation(id);


--
-- Name: td_besoinenpersonnel_bepfilieredisciplines fk1n9ll7ka9ivj7fel8sg18l59k; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_besoinenpersonnel_bepfilieredisciplines
    ADD CONSTRAINT fk1n9ll7ka9ivj7fel8sg18l59k FOREIGN KEY (besoinenpersonnel_id) REFERENCES schema_carriere.td_besoinenpersonnel(id);


--
-- Name: td_fichesynoptique_serieclasseprofdisciplines fk2h1ocmos2bnvrxhs2w20mtr5a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichesynoptique_serieclasseprofdisciplines
    ADD CONSTRAINT fk2h1ocmos2bnvrxhs2w20mtr5a FOREIGN KEY (fichesynoptique_id) REFERENCES schema_carriere.td_fichesynoptique(id);


--
-- Name: tr_serieniveaudiscipline_disciplinequantums fk2ts7bp9lap7bp3yk7u2xmd9rf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_serieniveaudiscipline_disciplinequantums
    ADD CONSTRAINT fk2ts7bp9lap7bp3yk7u2xmd9rf FOREIGN KEY (serieniveaudiscipline_id) REFERENCES schema_carriere.tr_serieniveaudiscipline(id);


--
-- Name: tr_user_role fk35yxfrxyncv4gxu08sko4bp06; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_user_role
    ADD CONSTRAINT fk35yxfrxyncv4gxu08sko4bp06 FOREIGN KEY (user_id) REFERENCES public.td_user(id);


--
-- Name: tr_filierediscipline_disciplinequantums fk37pq9w9twbg43b5mll9os4fwl; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_filierediscipline_disciplinequantums
    ADD CONSTRAINT fk37pq9w9twbg43b5mll9os4fwl FOREIGN KEY (disciplinequantums_id) REFERENCES schema_carriere.td_disciplinequantum(id);


--
-- Name: traitementpermutation_statut fk3edv8hxxfoy4x0cw0886r761h; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.traitementpermutation_statut
    ADD CONSTRAINT fk3edv8hxxfoy4x0cw0886r761h FOREIGN KEY (statutpermu_id) REFERENCES schema_carriere.tp_status_permutation(status_permutation_id);


--
-- Name: td_contract fk3hagixgyjrbrybbnas3qq9tmu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_contract
    ADD CONSTRAINT fk3hagixgyjrbrybbnas3qq9tmu FOREIGN KEY (user_id) REFERENCES public.td_user(id);


--
-- Name: td_fichesynoptique_filieredisciplines fk3w1qh2mdymocw9lrysfe5x7b3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichesynoptique_filieredisciplines
    ADD CONSTRAINT fk3w1qh2mdymocw9lrysfe5x7b3 FOREIGN KEY (filieredisciplines_id) REFERENCES schema_carriere.tr_filierediscipline(id);


--
-- Name: tr_user_role fk4upd1qshsulpveopi2b403wvr; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_user_role
    ADD CONSTRAINT fk4upd1qshsulpveopi2b403wvr FOREIGN KEY (role_id) REFERENCES public.td_role(id);


--
-- Name: param_bureau fk54y0v1dkcewtrurbo6qdvhvd1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.param_bureau
    ADD CONSTRAINT fk54y0v1dkcewtrurbo6qdvhvd1 FOREIGN KEY (bureau_id) REFERENCES schema_utilisateur.tp_bureau(bur_id);


--
-- Name: td_rapport_pv fk58oxf0mo07ekvo6wlww6sv2vh; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_rapport_pv
    ADD CONSTRAINT fk58oxf0mo07ekvo6wlww6sv2vh FOREIGN KEY (rapport_id) REFERENCES schema_formation.td_rapport(id);


--
-- Name: td_parapher fk59bfuedc1godweqtffvfmdij8; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_parapher
    ADD CONSTRAINT fk59bfuedc1godweqtffvfmdij8 FOREIGN KEY (user_id) REFERENCES public.td_user(id);


--
-- Name: tr_contract_file fk7o7y6ubmgkyd80h9mxr6c9eq1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_contract_file
    ADD CONSTRAINT fk7o7y6ubmgkyd80h9mxr6c9eq1 FOREIGN KEY (contract_id) REFERENCES public.td_contract(id);


--
-- Name: tp_profile_menus fk8229imdyj8f532a6ugoxongwc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_profile_menus
    ADD CONSTRAINT fk8229imdyj8f532a6ugoxongwc FOREIGN KEY (menus_pmsm_id) REFERENCES schema_utilisateur.tr_profilemenusousmenu(pmsm_id);


--
-- Name: td_planningformation_files fk82gb36qakjm5nvw06klrdr1qr; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_planningformation_files
    ADD CONSTRAINT fk82gb36qakjm5nvw06klrdr1qr FOREIGN KEY (files_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_fichesynoptique_serieclasseprofdisciplines fk87o4jnpbu8sfra64t46mv1vy9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichesynoptique_serieclasseprofdisciplines
    ADD CONSTRAINT fk87o4jnpbu8sfra64t46mv1vy9 FOREIGN KEY (serieclasseprofdisciplines_id) REFERENCES schema_carriere.tr_serieclasseprofdiscipline(id);


--
-- Name: td_contract fk8pyr2xa061rpi6oijegxja48x; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_contract
    ADD CONSTRAINT fk8pyr2xa061rpi6oijegxja48x FOREIGN KEY (file_id) REFERENCES public.td_file(id);


--
-- Name: td_utilisateur fk9qoi20mba1rqjijaywyc555bv; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_utilisateur
    ADD CONSTRAINT fk9qoi20mba1rqjijaywyc555bv FOREIGN KEY (uti_pro_id) REFERENCES public.tp_profil(pro_id);


--
-- Name: td_contract fka6yk8bapuofsuynw9ludk8txp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_contract
    ADD CONSTRAINT fka6yk8bapuofsuynw9ludk8txp FOREIGN KEY (process_id) REFERENCES public.tp_processing(id);


--
-- Name: td_fichiercanditure_files fka95jjp60dpv0u9rsbyvs4wucw; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichiercanditure_files
    ADD CONSTRAINT fka95jjp60dpv0u9rsbyvs4wucw FOREIGN KEY (files_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_demandestage_justificatfs fkb2qiui01ggsgjd1qqx7k8lgno; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_demandestage_justificatfs
    ADD CONSTRAINT fkb2qiui01ggsgjd1qqx7k8lgno FOREIGN KEY (justificatfs_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: param_division fkbgu74iir2qvtdc4lg4cln752k; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.param_division
    ADD CONSTRAINT fkbgu74iir2qvtdc4lg4cln752k FOREIGN KEY (param_id) REFERENCES schema_pta.td_parametre(param_id);


--
-- Name: tr_serieniveaudiscipline_disciplinequantums fkbrdk01n8vd898o1k17ekcd647; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_serieniveaudiscipline_disciplinequantums
    ADD CONSTRAINT fkbrdk01n8vd898o1k17ekcd647 FOREIGN KEY (disciplinequantums_id) REFERENCES schema_carriere.td_disciplinequantum(id);


--
-- Name: td_account fkc414m6ncpbgq7ect4x2r0pdqq; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_account
    ADD CONSTRAINT fkc414m6ncpbgq7ect4x2r0pdqq FOREIGN KEY (user_id) REFERENCES public.td_user(id);


--
-- Name: param_division fkchdqyy33tw4eqt5figxarr27h; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.param_division
    ADD CONSTRAINT fkchdqyy33tw4eqt5figxarr27h FOREIGN KEY (division_id) REFERENCES schema_utilisateur.tp_division(div_id);


--
-- Name: param_bureau fkd7emkdpvwepnu6mo2wqeqr8c2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.param_bureau
    ADD CONSTRAINT fkd7emkdpvwepnu6mo2wqeqr8c2 FOREIGN KEY (param_id) REFERENCES schema_pta.td_parametre(param_id);


--
-- Name: td_demandestage_justificatfs fkdm92hcwtshlc4uipfseacbre6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_demandestage_justificatfs
    ADD CONSTRAINT fkdm92hcwtshlc4uipfseacbre6 FOREIGN KEY (demandestage_demande_id) REFERENCES schema_formation.td_demandestage(demande_id);


--
-- Name: td_fichesynoptique_classeprofdisciplines fkdmqnvlqa36lbaibmmy83me3x9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichesynoptique_classeprofdisciplines
    ADD CONSTRAINT fkdmqnvlqa36lbaibmmy83me3x9 FOREIGN KEY (classeprofdisciplines_id) REFERENCES schema_carriere.tr_classeprofdiscipline(id);


--
-- Name: tr_contract_file fke64rd211jubims3kjp33sn2fs; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_contract_file
    ADD CONSTRAINT fke64rd211jubims3kjp33sn2fs FOREIGN KEY (file_additional_id) REFERENCES public.td_file(id);


--
-- Name: tr_serieclasseprofdiscipline_profdiscipline fkf3vaxm5ojli76kmx1d6qeqkso; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_serieclasseprofdiscipline_profdiscipline
    ADD CONSTRAINT fkf3vaxm5ojli76kmx1d6qeqkso FOREIGN KEY (serieclasseprofdiscipline_id) REFERENCES schema_carriere.tr_serieclasseprofdiscipline(id);


--
-- Name: tr_serieclasseprofdiscipline_profdiscipline fkf6acxkeoattoxjyh2spgs9qw1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_serieclasseprofdiscipline_profdiscipline
    ADD CONSTRAINT fkf6acxkeoattoxjyh2spgs9qw1 FOREIGN KEY (profdiscipline_id) REFERENCES schema_carriere.tr_profdiscipline(id);


--
-- Name: tp_profile_menus fkfhyeao1e0ox6cxrigqaeah8in; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tp_profile_menus
    ADD CONSTRAINT fkfhyeao1e0ox6cxrigqaeah8in FOREIGN KEY (profile_pro_id) REFERENCES schema_utilisateur.tp_profile(pro_id);


--
-- Name: token fkfjolry9up2hlh65qvkai9b5gd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT fkfjolry9up2hlh65qvkai9b5gd FOREIGN KEY (account_id) REFERENCES public.td_account(user_id);


--
-- Name: tr_filierediscipline_disciplinequantums fkg1xa2qmb5qpq86v5f1owcj6jc; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_filierediscipline_disciplinequantums
    ADD CONSTRAINT fkg1xa2qmb5qpq86v5f1owcj6jc FOREIGN KEY (filierediscipline_id) REFERENCES schema_carriere.tr_filierediscipline(id);


--
-- Name: td_offretechniquefinanciere_files fkggfucmabs6m2ysxcpbgdh8jit; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_offretechniquefinanciere_files
    ADD CONSTRAINT fkggfucmabs6m2ysxcpbgdh8jit FOREIGN KEY (files_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: tr_classeprofdiscipline_profdiscipline fkgu2261lia5m83a6gcvj5103ea; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_classeprofdiscipline_profdiscipline
    ADD CONSTRAINT fkgu2261lia5m83a6gcvj5103ea FOREIGN KEY (classeprofdiscipline_id) REFERENCES schema_carriere.tr_classeprofdiscipline(id);


--
-- Name: tr_profdiscipline_disciplinequantums fkh43kyyv4mpqpt7wgvp3b1fgl; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_profdiscipline_disciplinequantums
    ADD CONSTRAINT fkh43kyyv4mpqpt7wgvp3b1fgl FOREIGN KEY (profdiscipline_id) REFERENCES schema_carriere.tr_profdiscipline(id);


--
-- Name: td_fichesynoptique_classeprofdisciplines fkhn9s00b06pmypvu00xnafme89; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichesynoptique_classeprofdisciplines
    ADD CONSTRAINT fkhn9s00b06pmypvu00xnafme89 FOREIGN KEY (fichesynoptique_id) REFERENCES schema_carriere.td_fichesynoptique(id);


--
-- Name: td_offretechniquefinanciere_files fki4rpecve0icc49c1ut3hgpjlt; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_offretechniquefinanciere_files
    ADD CONSTRAINT fki4rpecve0icc49c1ut3hgpjlt FOREIGN KEY (offretechniquefinanciere_id) REFERENCES schema_formation.td_offretechniquefinanciere(id);


--
-- Name: tr_bepfilierediscipline_besoinennombredisciplines fki96ky7qxwfekuxnr38r6bulqo; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_bepfilierediscipline_besoinennombredisciplines
    ADD CONSTRAINT fki96ky7qxwfekuxnr38r6bulqo FOREIGN KEY (bepfilierediscipline_id) REFERENCES schema_carriere.tr_bepfilierediscipline(id);


--
-- Name: td_fichesynoptique_filieredisciplines fkjc5rcjl6qqfijddkrnpwk80lw; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichesynoptique_filieredisciplines
    ADD CONSTRAINT fkjc5rcjl6qqfijddkrnpwk80lw FOREIGN KEY (fichesynoptique_id) REFERENCES schema_carriere.td_fichesynoptique(id);


--
-- Name: td_fichiercanditure_files fkjg0wq63bqg2nl7p2qevtnnu53; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichiercanditure_files
    ADD CONSTRAINT fkjg0wq63bqg2nl7p2qevtnnu53 FOREIGN KEY (fichiercandidature_id) REFERENCES schema_formation.td_fichiercanditure(id);


--
-- Name: td_contract fkjq1vqhehhp8srfesommc7415g; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_contract
    ADD CONSTRAINT fkjq1vqhehhp8srfesommc7415g FOREIGN KEY (entity_id) REFERENCES public.td_entite(id);


--
-- Name: tr_profdiscipline_disciplinequantums fkk42otexwakt4p40ib1san4inu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_profdiscipline_disciplinequantums
    ADD CONSTRAINT fkk42otexwakt4p40ib1san4inu FOREIGN KEY (disciplinequantums_id) REFERENCES schema_carriere.td_disciplinequantum(id);


--
-- Name: tr_profilmenu fkl1c9nek2st8ihma0is2y6na38; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_profilmenu
    ADD CONSTRAINT fkl1c9nek2st8ihma0is2y6na38 FOREIGN KEY (pro_id) REFERENCES public.tp_profil(pro_id);


--
-- Name: tr_profilmenu fkl3actfmb4k3db1l65fx9jedua; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_profilmenu
    ADD CONSTRAINT fkl3actfmb4k3db1l65fx9jedua FOREIGN KEY (men_id) REFERENCES public.tp_menu(men_id);


--
-- Name: td_user fklwjjm31l9ufkb7f9egpfem8w; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_user
    ADD CONSTRAINT fklwjjm31l9ufkb7f9egpfem8w FOREIGN KEY (paraphe_id) REFERENCES public.td_file(id);


--
-- Name: td_piecejoint_files fkm0kn63r22ulxefteg6xa6kyp3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_piecejoint_files
    ADD CONSTRAINT fkm0kn63r22ulxefteg6xa6kyp3 FOREIGN KEY (piecejointes_pj_id) REFERENCES schema_carriere.td_piecejoint(pj_id);


--
-- Name: td_amendment fkm2ps72kjsagicpthlb8bfi2jq; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_amendment
    ADD CONSTRAINT fkm2ps72kjsagicpthlb8bfi2jq FOREIGN KEY (contract_id) REFERENCES public.td_contract(id);


--
-- Name: td_fichesynoptique_serieniveaudisciplines fkmcytyaklm0mxlmng3ykxtqac6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichesynoptique_serieniveaudisciplines
    ADD CONSTRAINT fkmcytyaklm0mxlmng3ykxtqac6 FOREIGN KEY (fichesynoptique_id) REFERENCES schema_carriere.td_fichesynoptique(id);


--
-- Name: td_planningformation_files fkmex4w0w9m5rwir3j8b56n0r53; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_planningformation_files
    ADD CONSTRAINT fkmex4w0w9m5rwir3j8b56n0r53 FOREIGN KEY (planningformation_id) REFERENCES schema_formation.td_planningformation(id);


--
-- Name: td_fichesynoptique_serieniveaudisciplines fknebvok6k7d9nq15eubs7fh1rr; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_fichesynoptique_serieniveaudisciplines
    ADD CONSTRAINT fknebvok6k7d9nq15eubs7fh1rr FOREIGN KEY (serieniveaudisciplines_id) REFERENCES schema_carriere.tr_serieniveaudiscipline(id);


--
-- Name: tr_contract_parapher fknpxkxwskk5mdy01v39yf3dipn; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_contract_parapher
    ADD CONSTRAINT fknpxkxwskk5mdy01v39yf3dipn FOREIGN KEY (parapher_id) REFERENCES public.td_parapher(id);


--
-- Name: td_planformation_files fknresgkwc7vhn6hxj6y2i6wmim; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_planformation_files
    ADD CONSTRAINT fknresgkwc7vhn6hxj6y2i6wmim FOREIGN KEY (planformation_id) REFERENCES schema_formation.td_planformation(id);


--
-- Name: td_rapport_pv fko6epfoq55btuhvvxfib3vndt4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_rapport_pv
    ADD CONSTRAINT fko6epfoq55btuhvvxfib3vndt4 FOREIGN KEY (pv_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: tr_contract_parapher fko6o7gxb0vcstxw62ygq1krk7w; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_contract_parapher
    ADD CONSTRAINT fko6o7gxb0vcstxw62ygq1krk7w FOREIGN KEY (contract_id) REFERENCES public.td_contract(id);


--
-- Name: td_amendment fko7882vfbo35wlrrqld2k08xdl; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_amendment
    ADD CONSTRAINT fko7882vfbo35wlrrqld2k08xdl FOREIGN KEY (parapher_id) REFERENCES public.td_parapher(id);


--
-- Name: traitementmutation_statut fko9b5oqutu0r3p7i458koua0ll; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.traitementmutation_statut
    ADD CONSTRAINT fko9b5oqutu0r3p7i458koua0ll FOREIGN KEY (traitement_mutation_id) REFERENCES schema_carriere.td_traitementmutation(id);


--
-- Name: td_piecejoint_files fkousq7bmsa8rjx77xs8gmdjnhx; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_piecejoint_files
    ADD CONSTRAINT fkousq7bmsa8rjx77xs8gmdjnhx FOREIGN KEY (files_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: tr_bepfilierediscipline_besoinennombredisciplines fkqbmhf3re69pr5h4b7aq6muq44; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_bepfilierediscipline_besoinennombredisciplines
    ADD CONSTRAINT fkqbmhf3re69pr5h4b7aq6muq44 FOREIGN KEY (besoinennombredisciplines_id) REFERENCES schema_carriere.tr_besoinennombrediscipline(id);


--
-- Name: td_demandestage_justificatfsauthorisationstage fkqoc3hi506a2cl92548y5h0ko4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_demandestage_justificatfsauthorisationstage
    ADD CONSTRAINT fkqoc3hi506a2cl92548y5h0ko4 FOREIGN KEY (demandestage_demande_id) REFERENCES schema_formation.td_demandestage(demande_id);


--
-- Name: td_besoinenpersonnel_bepfilieredisciplines fkr9kmedyqjqrwnspylvapxrmw6; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_besoinenpersonnel_bepfilieredisciplines
    ADD CONSTRAINT fkr9kmedyqjqrwnspylvapxrmw6 FOREIGN KEY (bepfilieredisciplines_id) REFERENCES schema_carriere.tr_bepfilierediscipline(id);


--
-- Name: td_user fkrefrujqd1fpt488cbrtv9pam9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_user
    ADD CONSTRAINT fkrefrujqd1fpt488cbrtv9pam9 FOREIGN KEY (file_id) REFERENCES public.td_file(id);


--
-- Name: tr_classeprofdiscipline_profdiscipline fkrsp2tj9mojl8db3v8dikgxfe1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tr_classeprofdiscipline_profdiscipline
    ADD CONSTRAINT fkrsp2tj9mojl8db3v8dikgxfe1 FOREIGN KEY (profdiscipline_id) REFERENCES schema_carriere.tr_profdiscipline(id);


--
-- Name: td_rapport_files fks6q8y5pskwd1jsa8j7a296ofe; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_rapport_files
    ADD CONSTRAINT fks6q8y5pskwd1jsa8j7a296ofe FOREIGN KEY (rapport_id) REFERENCES schema_formation.td_rapport(id);


--
-- Name: td_rapport_files fksoogl6u7ojhtgfe4phbtjvbnr; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_rapport_files
    ADD CONSTRAINT fksoogl6u7ojhtgfe4phbtjvbnr FOREIGN KEY (files_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_demandestage_justificatfsauthorisationstage fkt1liexnggwfs3ehn9pto0or8h; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_demandestage_justificatfsauthorisationstage
    ADD CONSTRAINT fkt1liexnggwfs3ehn9pto0or8h FOREIGN KEY (justificatfsauthorisationstage_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_planformation_files fktd5yqlcmv007d01dtv3exitno; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.td_planformation_files
    ADD CONSTRAINT fktd5yqlcmv007d01dtv3exitno FOREIGN KEY (files_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_priseencharge fk4ma8dj07rwscpbvrmjrs6ecdm; Type: FK CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_priseencharge
    ADD CONSTRAINT fk4ma8dj07rwscpbvrmjrs6ecdm FOREIGN KEY (pec_div_id) REFERENCES schema_utilisateur.tp_division(div_id);


--
-- Name: td_priseencharge fk981ylynnbg1r8gp1xx0rwt9su; Type: FK CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_priseencharge
    ADD CONSTRAINT fk981ylynnbg1r8gp1xx0rwt9su FOREIGN KEY (pec_cfp_id) REFERENCES schema_utilisateur.tp_cfp(cfp_id);


--
-- Name: td_priseencharge fk9rkjcucn7c3addpitmirxkkww; Type: FK CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_priseencharge
    ADD CONSTRAINT fk9rkjcucn7c3addpitmirxkkww FOREIGN KEY (pec_bur_id) REFERENCES schema_utilisateur.tp_bureau(bur_id);


--
-- Name: td_priseencharge fka62e6ay5hd3amwnb10u3ryafh; Type: FK CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_priseencharge
    ADD CONSTRAINT fka62e6ay5hd3amwnb10u3ryafh FOREIGN KEY (pec_etab_id) REFERENCES schema_utilisateur.tp_typeetablissement(eta_id);


--
-- Name: td_priseencharge fkdrtatr7uytd8oixmhluvs16o0; Type: FK CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_priseencharge
    ADD CONSTRAINT fkdrtatr7uytd8oixmhluvs16o0 FOREIGN KEY (pec_region_id) REFERENCES schema_utilisateur.tp_region(reg_id);


--
-- Name: td_priseencharge fkeetlbqwrirsaov6put04wtfcp; Type: FK CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_priseencharge
    ADD CONSTRAINT fkeetlbqwrirsaov6put04wtfcp FOREIGN KEY (pec_ser_id) REFERENCES schema_utilisateur.tp_service(ser_id);


--
-- Name: td_priseencharge fkeptihxurr6xr7aa5ounl6sbfe; Type: FK CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_priseencharge
    ADD CONSTRAINT fkeptihxurr6xr7aa5ounl6sbfe FOREIGN KEY (pec_stat_id) REFERENCES schema_affairesociale.tp_statutpriseencharge(stat_id);


--
-- Name: td_priseencharge fkf8ti9mbbu5xb80rvl7ssia4gy; Type: FK CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_priseencharge
    ADD CONSTRAINT fkf8ti9mbbu5xb80rvl7ssia4gy FOREIGN KEY (pec_type_id) REFERENCES schema_affairesociale.tp_typepriseencharge(typepec_id);


--
-- Name: td_priseencharge fki7w4j7f5big9idf9gaq6akkhb; Type: FK CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_priseencharge
    ADD CONSTRAINT fki7w4j7f5big9idf9gaq6akkhb FOREIGN KEY (pec_dir_id) REFERENCES schema_utilisateur.tp_direction(dir_id);


--
-- Name: td_priseencharge fkis5ohkd0okw87yfj1q3odf052; Type: FK CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_priseencharge
    ADD CONSTRAINT fkis5ohkd0okw87yfj1q3odf052 FOREIGN KEY (pec_ief_id) REFERENCES schema_utilisateur.tp_ief(seq_ief);


--
-- Name: td_priseencharge fkj7eay4xifyy7mckgmkli7jwkd; Type: FK CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_priseencharge
    ADD CONSTRAINT fkj7eay4xifyy7mckgmkli7jwkd FOREIGN KEY (pec_eff_id) REFERENCES schema_utilisateur.tp_eef(eef_id);


--
-- Name: td_priseencharge fkpwd4028skb2tq3xtihr8esci7; Type: FK CONSTRAINT; Schema: schema_affairesociale; Owner: -
--

ALTER TABLE ONLY schema_affairesociale.td_priseencharge
    ADD CONSTRAINT fkpwd4028skb2tq3xtihr8esci7 FOREIGN KEY (pec_ia_id) REFERENCES schema_utilisateur.tp_ia(ia_id);


--
-- Name: td_acte fk10eb2xuvh4ng9rax815xanv5a; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fk10eb2xuvh4ng9rax815xanv5a FOREIGN KEY (typeag_id) REFERENCES schema_carriere.tp_typeaa(typeaa_id);


--
-- Name: td_acte fk18l1k8nleggaexjwvv61u0pu6; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fk18l1k8nleggaexjwvv61u0pu6 FOREIGN KEY (type_acte_id) REFERENCES schema_carriere.tp_typeacte(type_id);


--
-- Name: td_permutation fk1ij51kk258rmndsrc7qgp8v5f; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_permutation
    ADD CONSTRAINT fk1ij51kk258rmndsrc7qgp8v5f FOREIGN KEY (ief_receveur) REFERENCES schema_utilisateur.tp_ief(seq_ief);


--
-- Name: tr_serieclasseprofdiscipline fk1lw3d7yfgmg5i567ufblvc7jn; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_serieclasseprofdiscipline
    ADD CONSTRAINT fk1lw3d7yfgmg5i567ufblvc7jn FOREIGN KEY (serie_id) REFERENCES schema_carriere.tp_serie(id);


--
-- Name: td_besoinenpersonnel fk1vgab7yqxcq9gkptotk8l4oph; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_besoinenpersonnel
    ADD CONSTRAINT fk1vgab7yqxcq9gkptotk8l4oph FOREIGN KEY (cor_id) REFERENCES schema_utilisateur.tp_corps(cor_id);


--
-- Name: td_mutation fk1vrjl7cgbihu7p6io18k309kl; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_mutation
    ADD CONSTRAINT fk1vrjl7cgbihu7p6io18k309kl FOREIGN KEY (ief_id) REFERENCES schema_utilisateur.tp_ief(seq_ief);


--
-- Name: td_besoinenpersonnel fk1xppxh8hvvghvvs249aft8ili; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_besoinenpersonnel
    ADD CONSTRAINT fk1xppxh8hvvghvvs249aft8ili FOREIGN KEY (ief_id) REFERENCES schema_utilisateur.tp_ief(seq_ief);


--
-- Name: td_agent fk23g0hhoerdjw3rdkaddnunq44; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_agent
    ADD CONSTRAINT fk23g0hhoerdjw3rdkaddnunq44 FOREIGN KEY (dosid) REFERENCES schema_carriere.td_dossieragent(dos_id);


--
-- Name: td_acte fk254t25rwba20j2w38e295pokn; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fk254t25rwba20j2w38e295pokn FOREIGN KEY (dossier_agent_id) REFERENCES schema_carriere.td_dossieragent(dos_id);


--
-- Name: td_situation_administrative fk353jtvqd7077c0ot0e68kf301; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_situation_administrative
    ADD CONSTRAINT fk353jtvqd7077c0ot0e68kf301 FOREIGN KEY (dossier_agent_id) REFERENCES schema_carriere.td_dossieragent(dos_id);


--
-- Name: td_mutation fk3em242cq49a823tlj6xu6h48j; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_mutation
    ADD CONSTRAINT fk3em242cq49a823tlj6xu6h48j FOREIGN KEY (bureau_id) REFERENCES schema_utilisateur.tp_bureau(bur_id);


--
-- Name: tr_profdiscipline fk3o48uwho1yhlj97ntelvfslxx; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_profdiscipline
    ADD CONSTRAINT fk3o48uwho1yhlj97ntelvfslxx FOREIGN KEY (deconcentratedlevel_id) REFERENCES schema_utilisateur.td_deconcentratedlevel(id);


--
-- Name: td_etatcivil fk44rum9fstuhs3f9wdikp7u30; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_etatcivil
    ADD CONSTRAINT fk44rum9fstuhs3f9wdikp7u30 FOREIGN KEY (dossier_agent_id) REFERENCES schema_carriere.td_dossieragent(dos_id);


--
-- Name: td_besoinenpersonnel fk4kr1o2y1x74h2bnj23easjrrq; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_besoinenpersonnel
    ADD CONSTRAINT fk4kr1o2y1x74h2bnj23easjrrq FOREIGN KEY (etablissement_id) REFERENCES schema_utilisateur.tp_typeetablissement(eta_id);


--
-- Name: td_traitementacte fk4tbi6413830u5d40ntdtduife; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_traitementacte
    ADD CONSTRAINT fk4tbi6413830u5d40ntdtduife FOREIGN KEY (acte_id) REFERENCES schema_carriere.td_acte(acte_id);


--
-- Name: td_mutation fk5uo1s9dr7wrudsblejrh2rqpy; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_mutation
    ADD CONSTRAINT fk5uo1s9dr7wrudsblejrh2rqpy FOREIGN KEY (division_id) REFERENCES schema_utilisateur.tp_division(div_id);


--
-- Name: td_originedemandeurlog fk775sh79fgomavmi0k4y9uw5fe; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_originedemandeurlog
    ADD CONSTRAINT fk775sh79fgomavmi0k4y9uw5fe FOREIGN KEY (reg_id) REFERENCES schema_utilisateur.tp_region(reg_id);


--
-- Name: td_mutation fk7dld864gp4agc390a625aos63; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_mutation
    ADD CONSTRAINT fk7dld864gp4agc390a625aos63 FOREIGN KEY (traitementmutation_id) REFERENCES schema_carriere.td_traitementmutation(id);


--
-- Name: td_besoinenpersonnel fk7dxarodf2o71sgqwwsq2hktlt; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_besoinenpersonnel
    ADD CONSTRAINT fk7dxarodf2o71sgqwwsq2hktlt FOREIGN KEY (ia_id) REFERENCES schema_utilisateur.tp_ia(ia_id);


--
-- Name: td_situation_administrative fk8qfcef1347vsg68pprw4jgkt; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_situation_administrative
    ADD CONSTRAINT fk8qfcef1347vsg68pprw4jgkt FOREIGN KEY (typeag_id) REFERENCES schema_carriere.tp_typeaa(typeaa_id);


--
-- Name: td_permutation fk97d7u98jl58wpvoeekdup575c; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_permutation
    ADD CONSTRAINT fk97d7u98jl58wpvoeekdup575c FOREIGN KEY (ia_demandeur) REFERENCES schema_utilisateur.tp_ia(ia_id);


--
-- Name: td_fichesynoptique fkav2l57cyipex93snhgx7y6351; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_fichesynoptique
    ADD CONSTRAINT fkav2l57cyipex93snhgx7y6351 FOREIGN KEY (deconcentredlevelid) REFERENCES schema_utilisateur.td_deconcentratedlevel(id);


--
-- Name: td_originedemandeurlog fkbcmchxjav2sktylus751m9njm; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_originedemandeurlog
    ADD CONSTRAINT fkbcmchxjav2sktylus751m9njm FOREIGN KEY (direction_id) REFERENCES schema_utilisateur.tp_direction(dir_id);


--
-- Name: tp_serie fkbteqrfu95epkxv0y0b8dcwfcr; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_serie
    ADD CONSTRAINT fkbteqrfu95epkxv0y0b8dcwfcr FOREIGN KEY (formation_pro_id) REFERENCES schema_carriere.tp_formation_prof(id);


--
-- Name: td_mutation fkc8sy3ae2ax3kblh7xso5e064o; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_mutation
    ADD CONSTRAINT fkc8sy3ae2ax3kblh7xso5e064o FOREIGN KEY (etablissement_id) REFERENCES schema_utilisateur.tp_typeetablissement(eta_id);


--
-- Name: tr_filierediscipline fkclaffwwvemh61984pv955q0wk; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_filierediscipline
    ADD CONSTRAINT fkclaffwwvemh61984pv955q0wk FOREIGN KEY (filiere_id) REFERENCES schema_carriere.tp_filiere(id);


--
-- Name: td_situation_administrative fkdakfco2gqatn5y9qoe6g0281b; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_situation_administrative
    ADD CONSTRAINT fkdakfco2gqatn5y9qoe6g0281b FOREIGN KEY (piecejointes_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_acte fkdvxhylm92lxexda66l68sdit6; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fkdvxhylm92lxexda66l68sdit6 FOREIGN KEY (acte_ia_id) REFERENCES schema_utilisateur.tp_ia(ia_id);


--
-- Name: td_permutation fkdxsr06tuiftlk3b0clw03nul8; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_permutation
    ADD CONSTRAINT fkdxsr06tuiftlk3b0clw03nul8 FOREIGN KEY (traitementpermutation_traite_id) REFERENCES schema_carriere.td_traitement_permutation(traite_id);


--
-- Name: td_acte fke3qbn3yv0k31ejt51m0k4dqx4; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fke3qbn3yv0k31ejt51m0k4dqx4 FOREIGN KEY (currentbordereau_bord_id) REFERENCES schema_carriere.tp_bordereau(bord_id);


--
-- Name: td_besoinenpersonnel fkflct60fres89gbjbqg50bik89; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_besoinenpersonnel
    ADD CONSTRAINT fkflct60fres89gbjbqg50bik89 FOREIGN KEY (grade_id) REFERENCES schema_utilisateur.tp_grade(grade_id);


--
-- Name: td_diplome fkfmt1orej4agurmdg098avyb2a; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_diplome
    ADD CONSTRAINT fkfmt1orej4agurmdg098avyb2a FOREIGN KEY (piecejointes_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_traitement_permutation fkfryg5a71kq2kjh35qcipikidy; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_traitement_permutation
    ADD CONSTRAINT fkfryg5a71kq2kjh35qcipikidy FOREIGN KEY (bordereauvalidation_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_acte fkg1stlptkfr7nyh4ovlvtw67qc; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fkg1stlptkfr7nyh4ovlvtw67qc FOREIGN KEY (acte_ser_id) REFERENCES schema_utilisateur.tp_service(ser_id);


--
-- Name: tr_bepfilierediscipline fkgda323c15kt7j8k447ty3ecij; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_bepfilierediscipline
    ADD CONSTRAINT fkgda323c15kt7j8k447ty3ecij FOREIGN KEY (filiere_id) REFERENCES schema_carriere.tp_filiere(id);


--
-- Name: td_diplome fkgmaajs8pvhowr6gs93e34m9o9; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_diplome
    ADD CONSTRAINT fkgmaajs8pvhowr6gs93e34m9o9 FOREIGN KEY (dossier_agent_id) REFERENCES schema_carriere.td_dossieragent(dos_id);


--
-- Name: td_acte fkgmx2n1csvhmtckmya96v6rx8o; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fkgmx2n1csvhmtckmya96v6rx8o FOREIGN KEY (typeaa_id) REFERENCES schema_carriere.tp_typeag(typeag_id);


--
-- Name: td_actualite fkh8ecr9dcboxg46elevo5betkx; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_actualite
    ADD CONSTRAINT fkh8ecr9dcboxg46elevo5betkx FOREIGN KEY (type_art_id) REFERENCES schema_carriere.tp_type_article(type_art_id);


--
-- Name: td_mutation fkhjkk7nhjef66cb7s6er34nnk5; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_mutation
    ADD CONSTRAINT fkhjkk7nhjef66cb7s6er34nnk5 FOREIGN KEY (direction_id) REFERENCES schema_utilisateur.tp_direction(dir_id);


--
-- Name: td_originedemandeurlog fkhrffoyqeiygh9akasfveqis19; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_originedemandeurlog
    ADD CONSTRAINT fkhrffoyqeiygh9akasfveqis19 FOREIGN KEY (service_id) REFERENCES schema_utilisateur.tp_service(ser_id);


--
-- Name: td_permutation fkhtpxbx4nvy0ywf5k5at952pdx; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_permutation
    ADD CONSTRAINT fkhtpxbx4nvy0ywf5k5at952pdx FOREIGN KEY (etab_receveur) REFERENCES schema_utilisateur.tp_typeetablissement(eta_id);


--
-- Name: td_situation_administrative fkio0pww3pi481j3l3jeedwj790; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_situation_administrative
    ADD CONSTRAINT fkio0pww3pi481j3l3jeedwj790 FOREIGN KEY (type_acte_id) REFERENCES schema_carriere.tp_typeacte(type_id);


--
-- Name: td_originedemandeurlog fkis1t8gwc8ut7vba0fr0oy4em2; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_originedemandeurlog
    ADD CONSTRAINT fkis1t8gwc8ut7vba0fr0oy4em2 FOREIGN KEY (etablissement_id) REFERENCES schema_utilisateur.tp_typeetablissement(eta_id);


--
-- Name: tr_filierediscipline fkiu8xescxnhapqrcy53unxu3ea; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_filierediscipline
    ADD CONSTRAINT fkiu8xescxnhapqrcy53unxu3ea FOREIGN KEY (niveau_id) REFERENCES schema_carriere.tp_niveau(id);


--
-- Name: td_actualite fkjcqllpuvyep4f9n9xhnpm1lls; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_actualite
    ADD CONSTRAINT fkjcqllpuvyep4f9n9xhnpm1lls FOREIGN KEY (image_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: tr_serieniveaudiscipline fkjdytufv9s2frfod2g0pc33moo; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_serieniveaudiscipline
    ADD CONSTRAINT fkjdytufv9s2frfod2g0pc33moo FOREIGN KEY (niveau_id) REFERENCES schema_carriere.tp_niveau(id);


--
-- Name: td_acte fkjfkdyppu954k9dd38d87vks37; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fkjfkdyppu954k9dd38d87vks37 FOREIGN KEY (acte_etab_id) REFERENCES schema_utilisateur.tp_typeetablissement(eta_id);


--
-- Name: td_situation_administrative fkki7rpnp6cqc9b6n6que5nlvjb; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_situation_administrative
    ADD CONSTRAINT fkki7rpnp6cqc9b6n6que5nlvjb FOREIGN KEY (typeaa_id) REFERENCES schema_carriere.tp_typeag(typeag_id);


--
-- Name: tr_besoinennombrediscipline fkkj1t88ydg5y8ddn1gbn0yky73; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_besoinennombrediscipline
    ADD CONSTRAINT fkkj1t88ydg5y8ddn1gbn0yky73 FOREIGN KEY (discipline_id) REFERENCES schema_carriere.tp_discipline(id);


--
-- Name: td_mutation fkko1lf3w7yxhjlxgqvfd076ybm; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_mutation
    ADD CONSTRAINT fkko1lf3w7yxhjlxgqvfd076ybm FOREIGN KEY (ia_id) REFERENCES schema_utilisateur.tp_ia(ia_id);


--
-- Name: td_besoinenpersonnel fkktkm23lb19uc1n5nsv61sreij; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_besoinenpersonnel
    ADD CONSTRAINT fkktkm23lb19uc1n5nsv61sreij FOREIGN KEY (reg_id) REFERENCES schema_utilisateur.tp_region(reg_id);


--
-- Name: td_avancement fkkv9x2ptd1np8udmiy7vpkkwdo; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_avancement
    ADD CONSTRAINT fkkv9x2ptd1np8udmiy7vpkkwdo FOREIGN KEY (piecejointes_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_acte fkmdy7nxr0h68kb4utjcw8wf5gf; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fkmdy7nxr0h68kb4utjcw8wf5gf FOREIGN KEY (acte_eff_id) REFERENCES schema_utilisateur.tp_eef(eef_id);


--
-- Name: td_acte fkmf381txrfcp1slmlcktdofuv9; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fkmf381txrfcp1slmlcktdofuv9 FOREIGN KEY (acte_div_id) REFERENCES schema_utilisateur.tp_division(div_id);


--
-- Name: td_acte fkmvipcgk7kde6h7nlahlyymbk5; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fkmvipcgk7kde6h7nlahlyymbk5 FOREIGN KEY (acte_dir_id) REFERENCES schema_utilisateur.tp_direction(dir_id);


--
-- Name: td_disciplinequantum fknpjj7bb2d5s0u2qegn063p0yg; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_disciplinequantum
    ADD CONSTRAINT fknpjj7bb2d5s0u2qegn063p0yg FOREIGN KEY (discipline_id) REFERENCES schema_carriere.tp_discipline(id);


--
-- Name: td_mutation fknr1rs1wj7lj3t1qk20etv9oyp; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_mutation
    ADD CONSTRAINT fknr1rs1wj7lj3t1qk20etv9oyp FOREIGN KEY (reg_id) REFERENCES schema_utilisateur.tp_region(reg_id);


--
-- Name: td_permutation fkov4ak48sk1hhotd6bcymactts; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_permutation
    ADD CONSTRAINT fkov4ak48sk1hhotd6bcymactts FOREIGN KEY (ief_demandeur) REFERENCES schema_utilisateur.tp_ief(seq_ief);


--
-- Name: td_originedemandeurlog fkp6mmi2waraxvp76egwesrmd3u; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_originedemandeurlog
    ADD CONSTRAINT fkp6mmi2waraxvp76egwesrmd3u FOREIGN KEY (bureau_id) REFERENCES schema_utilisateur.tp_bureau(bur_id);


--
-- Name: td_permutation fkpd070vm30lnunwxlfxrn1fb8i; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_permutation
    ADD CONSTRAINT fkpd070vm30lnunwxlfxrn1fb8i FOREIGN KEY (ia_receveur) REFERENCES schema_utilisateur.tp_ia(ia_id);


--
-- Name: td_etatcivil fkpnlk5kls6djfy17ogdmancu4v; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_etatcivil
    ADD CONSTRAINT fkpnlk5kls6djfy17ogdmancu4v FOREIGN KEY (piecejointes_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: tr_serieniveaudiscipline fkpogijrj3af5nlmokyn1g2cshh; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tr_serieniveaudiscipline
    ADD CONSTRAINT fkpogijrj3af5nlmokyn1g2cshh FOREIGN KEY (serie_id) REFERENCES schema_carriere.tp_serie(id);


--
-- Name: td_agent fkqfeihrd8r2cmkcetdqj5hqmcf; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_agent
    ADD CONSTRAINT fkqfeihrd8r2cmkcetdqj5hqmcf FOREIGN KEY (deconcentredlevelid) REFERENCES schema_utilisateur.td_deconcentratedlevel(id);


--
-- Name: td_originedemandeurlog fkqp7htdx3xckk7g2on55j6u50t; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_originedemandeurlog
    ADD CONSTRAINT fkqp7htdx3xckk7g2on55j6u50t FOREIGN KEY (division_id) REFERENCES schema_utilisateur.tp_division(div_id);


--
-- Name: td_acte fkqtactapu3cpfdxjo9hevkvono; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fkqtactapu3cpfdxjo9hevkvono FOREIGN KEY (statut_acte_id) REFERENCES schema_carriere.tp_statutacte(stat_id);


--
-- Name: td_acte fkr17ue84q1r5wtwd0iknocy8wb; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fkr17ue84q1r5wtwd0iknocy8wb FOREIGN KEY (acte_ief_id) REFERENCES schema_utilisateur.tp_ief(seq_ief);


--
-- Name: td_originedemandeurlog fkr4w6hd5fg1y5xcvu5un4tgife; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_originedemandeurlog
    ADD CONSTRAINT fkr4w6hd5fg1y5xcvu5un4tgife FOREIGN KEY (ia_id) REFERENCES schema_utilisateur.tp_ia(ia_id);


--
-- Name: td_traitement_permutation fkr7og1dhxiet6ktro6dfgwvkxu; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_traitement_permutation
    ADD CONSTRAINT fkr7og1dhxiet6ktro6dfgwvkxu FOREIGN KEY (statutpermu_id) REFERENCES schema_carriere.tp_status_permutation(status_permutation_id);


--
-- Name: tp_niveau fkrg24k4sj4aeaknce5yds6lnet; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.tp_niveau
    ADD CONSTRAINT fkrg24k4sj4aeaknce5yds6lnet FOREIGN KEY (formation_pro_id) REFERENCES schema_carriere.tp_formation_prof(id);


--
-- Name: td_originedemandeurlog fkrpyxocst7t47fro2bc7c201qv; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_originedemandeurlog
    ADD CONSTRAINT fkrpyxocst7t47fro2bc7c201qv FOREIGN KEY (ief_id) REFERENCES schema_utilisateur.tp_ief(seq_ief);


--
-- Name: td_permutation fks1adb650vvsj4xnnoioat3u9o; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_permutation
    ADD CONSTRAINT fks1adb650vvsj4xnnoioat3u9o FOREIGN KEY (etab_demandeur) REFERENCES schema_utilisateur.tp_typeetablissement(eta_id);


--
-- Name: td_acte fksh1c33ht0otxalf0a1mqoef1q; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fksh1c33ht0otxalf0a1mqoef1q FOREIGN KEY (acte_cfp_id) REFERENCES schema_utilisateur.tp_cfp(cfp_id);


--
-- Name: td_actualite fksjrqhgfp4cwtsw3q61192pmkp; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_actualite
    ADD CONSTRAINT fksjrqhgfp4cwtsw3q61192pmkp FOREIGN KEY (cat_actu_id) REFERENCES schema_carriere.tp_categorieactualite(cat_actu_id);


--
-- Name: td_mutation fksrnrfm8y68233hmpqyj2y5hi; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_mutation
    ADD CONSTRAINT fksrnrfm8y68233hmpqyj2y5hi FOREIGN KEY (originedemandeurlog_id) REFERENCES schema_carriere.td_originedemandeurlog(id);


--
-- Name: td_mutation fkt47sxna9unlup705dchay8ri4; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_mutation
    ADD CONSTRAINT fkt47sxna9unlup705dchay8ri4 FOREIGN KEY (service_id) REFERENCES schema_utilisateur.tp_service(ser_id);


--
-- Name: td_acte fktaketk4wtlaq9h3kgl6y7retw; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fktaketk4wtlaq9h3kgl6y7retw FOREIGN KEY (predbordereau_bord_id) REFERENCES schema_carriere.tp_bordereau(bord_id);


--
-- Name: td_acte fkxqdkvq7ixw9j4isas0pi9vil; Type: FK CONSTRAINT; Schema: schema_carriere; Owner: -
--

ALTER TABLE ONLY schema_carriere.td_acte
    ADD CONSTRAINT fkxqdkvq7ixw9j4isas0pi9vil FOREIGN KEY (acte_bur_id) REFERENCES schema_utilisateur.tp_bureau(bur_id);


--
-- Name: td_avisdemandestage fk3nmtqkvdrk3jvrpfqtebq8ylo; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_avisdemandestage
    ADD CONSTRAINT fk3nmtqkvdrk3jvrpfqtebq8ylo FOREIGN KEY (avis_demandestage) REFERENCES schema_formation.td_demandestage(demande_id);


--
-- Name: td_traitementcourrier fk3qo26p18rs85qctn232lawpxd; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_traitementcourrier
    ADD CONSTRAINT fk3qo26p18rs85qctn232lawpxd FOREIGN KEY (traitcou_statutcourrier) REFERENCES schema_formation.tp_statutcourrier(stcou_id);


--
-- Name: td_demandestage fk3unij90dorelo8ru1res35jko; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_demandestage
    ADD CONSTRAINT fk3unij90dorelo8ru1res35jko FOREIGN KEY (demande_service) REFERENCES schema_utilisateur.tp_service(ser_id);


--
-- Name: td_participant fk497wowto4vm45fjf8wx9pjcb; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_participant
    ADD CONSTRAINT fk497wowto4vm45fjf8wx9pjcb FOREIGN KEY (participant_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_traitementdemandestage fk4tcgxx0me31ifay63ogqwe4e9; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_traitementdemandestage
    ADD CONSTRAINT fk4tcgxx0me31ifay63ogqwe4e9 FOREIGN KEY (traitdeman_utilisateur) REFERENCES schema_utilisateur.td_centrallevel(id);


--
-- Name: td_convocation fk5j4tyt3u1m9nwnsafn8ohw9jy; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_convocation
    ADD CONSTRAINT fk5j4tyt3u1m9nwnsafn8ohw9jy FOREIGN KEY (tdr_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_formation fk6gxdelb20tk12q70irtpc13ma; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_formation
    ADD CONSTRAINT fk6gxdelb20tk12q70irtpc13ma FOREIGN KEY (type_formation_id) REFERENCES schema_formation.td_type_formation(id);


--
-- Name: td_demandestage fk6vsirtt1i65684u0clkxvxapp; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_demandestage
    ADD CONSTRAINT fk6vsirtt1i65684u0clkxvxapp FOREIGN KEY (demande_niveauscolaire) REFERENCES schema_formation.tp_niveauscolaire(niveau_id);


--
-- Name: td_pvexamen fk7moa273rjuabb4irbb2cqenrg; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_pvexamen
    ADD CONSTRAINT fk7moa273rjuabb4irbb2cqenrg FOREIGN KEY (rapport_file_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_participant_definitif fk848x9clkg4qmrb97eu9drurw1; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_participant_definitif
    ADD CONSTRAINT fk848x9clkg4qmrb97eu9drurw1 FOREIGN KEY (formation_id) REFERENCES schema_formation.td_formation(id);


--
-- Name: td_campagne fk8ch71dh1urtpw5cnl7i21mvhy; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_campagne
    ADD CONSTRAINT fk8ch71dh1urtpw5cnl7i21mvhy FOREIGN KEY (centrallevel_id) REFERENCES schema_utilisateur.td_centrallevel(id);


--
-- Name: td_rapportstage fk8rw7nfjmkawmgd899klp70per; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_rapportstage
    ADD CONSTRAINT fk8rw7nfjmkawmgd899klp70per FOREIGN KEY (rapport_utilisateur) REFERENCES schema_utilisateur.td_centrallevel(id);


--
-- Name: td_rapportstage fk8x1m2ucbvrqveil94052u105g; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_rapportstage
    ADD CONSTRAINT fk8x1m2ucbvrqveil94052u105g FOREIGN KEY (piecesjoint_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_offretechniquefinanciere fk9koj6ysnrs9ffmy2rc7au2etl; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_offretechniquefinanciere
    ADD CONSTRAINT fk9koj6ysnrs9ffmy2rc7au2etl FOREIGN KEY (formation_id) REFERENCES schema_formation.td_formation(id);


--
-- Name: td_traitementdemandestage fka3pa9efsapqgua1d7oscg8ryy; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_traitementdemandestage
    ADD CONSTRAINT fka3pa9efsapqgua1d7oscg8ryy FOREIGN KEY (traitexp_statutdemandestage) REFERENCES schema_formation.tp_statutdemandestage(statudeman_id);


--
-- Name: td_themeformation fka780lpogil5cjojdvbvrhri7j; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_themeformation
    ADD CONSTRAINT fka780lpogil5cjojdvbvrhri7j FOREIGN KEY (direction_id) REFERENCES schema_utilisateur.tp_direction(dir_id);


--
-- Name: td_traitementcampagne fkc11qci6h1149lwdoins9obljc; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_traitementcampagne
    ADD CONSTRAINT fkc11qci6h1149lwdoins9obljc FOREIGN KEY (traitcam_campagne) REFERENCES schema_formation.td_campagne(cam_id);


--
-- Name: tr_theme_formation_profile fkc8x301ocomabmtl22rq9yvey6; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tr_theme_formation_profile
    ADD CONSTRAINT fkc8x301ocomabmtl22rq9yvey6 FOREIGN KEY (theme_id) REFERENCES schema_formation.td_themeformation(id);


--
-- Name: td_formation fkcsussq9d49kqgitdf1l0nxlml; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_formation
    ADD CONSTRAINT fkcsussq9d49kqgitdf1l0nxlml FOREIGN KEY (cahier_charge_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_demandestage fkda70lci4hq34qjrdmppcxl9r0; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_demandestage
    ADD CONSTRAINT fkda70lci4hq34qjrdmppcxl9r0 FOREIGN KEY (demande_bureau) REFERENCES schema_utilisateur.tp_bureau(bur_id);


--
-- Name: td_participation fke2m4mikcght2j99k2nik3w9lj; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_participation
    ADD CONSTRAINT fke2m4mikcght2j99k2nik3w9lj FOREIGN KEY (formation_id) REFERENCES schema_formation.td_formation(id);


--
-- Name: td_rapport fke6654nhek76gwncmib0yrpv8q; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_rapport
    ADD CONSTRAINT fke6654nhek76gwncmib0yrpv8q FOREIGN KEY (formation_id) REFERENCES schema_formation.td_formation(id);


--
-- Name: td_traitementcampagne fkebiwxhm16vrwnkfpqxbat7wxy; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_traitementcampagne
    ADD CONSTRAINT fkebiwxhm16vrwnkfpqxbat7wxy FOREIGN KEY (traitcam_statutcampagne) REFERENCES schema_formation.tp_statutcampagne(stcam_id);


--
-- Name: td_traitementcourrier fkejct14mnamcfin1qlyciejxvl; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_traitementcourrier
    ADD CONSTRAINT fkejct14mnamcfin1qlyciejxvl FOREIGN KEY (traitcou_courrier) REFERENCES schema_formation.td_courrier(courrier_id);


--
-- Name: td_convocation fkf15aw7f56ahotojew87fqkl83; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_convocation
    ADD CONSTRAINT fkf15aw7f56ahotojew87fqkl83 FOREIGN KEY (formation_id) REFERENCES schema_formation.td_formation(id);


--
-- Name: td_fichiercanditure fkf9rjlu0ww5go8rqc79ipysw8t; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_fichiercanditure
    ADD CONSTRAINT fkf9rjlu0ww5go8rqc79ipysw8t FOREIGN KEY (chefeff_id) REFERENCES schema_utilisateur.td_deconcentratedlevel(id);


--
-- Name: td_offretechniquefinanciere fkfbcnvq7vfp4b33onochf0x475; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_offretechniquefinanciere
    ADD CONSTRAINT fkfbcnvq7vfp4b33onochf0x475 FOREIGN KEY (chefeff_id) REFERENCES schema_utilisateur.td_deconcentratedlevel(id);


--
-- Name: td_traitementdemandestage fkfrtdhlshpro0koycloe9in2gt; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_traitementdemandestage
    ADD CONSTRAINT fkfrtdhlshpro0koycloe9in2gt FOREIGN KEY (traitdeman_demandestage) REFERENCES schema_formation.td_demandestage(demande_id);


--
-- Name: td_traitementexpressiondebesoin fkg3m6qcnjpcuq22j3g50sy14um; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_traitementexpressiondebesoin
    ADD CONSTRAINT fkg3m6qcnjpcuq22j3g50sy14um FOREIGN KEY (traitexp_expressiondebesoin) REFERENCES schema_formation.td_expressiondebesoin(exp_id);


--
-- Name: td_planningformation fkgh7qgxf95p6nj6ndomhs4ky8x; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_planningformation
    ADD CONSTRAINT fkgh7qgxf95p6nj6ndomhs4ky8x FOREIGN KEY (formation_id) REFERENCES schema_formation.td_formation(id);


--
-- Name: td_offretechniquefinanciere fkh2uf3qdyejexof3q2540rsyj3; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_offretechniquefinanciere
    ADD CONSTRAINT fkh2uf3qdyejexof3q2540rsyj3 FOREIGN KEY (statut_offre_technique_id) REFERENCES schema_formation.td_statut_offre_technique(id);


--
-- Name: td_session fkhu7silryo4ugwu2nirpegd06l; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_session
    ADD CONSTRAINT fkhu7silryo4ugwu2nirpegd06l FOREIGN KEY (formation_id) REFERENCES schema_formation.td_formation(id);


--
-- Name: td_demandestage fkhw2n69yygey7ucgda90jfldj1; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_demandestage
    ADD CONSTRAINT fkhw2n69yygey7ucgda90jfldj1 FOREIGN KEY (demande_centrallevel) REFERENCES schema_utilisateur.td_centrallevel(id);


--
-- Name: td_attestationstage fkj9bca8yvffhyjmbknrabmgupe; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_attestationstage
    ADD CONSTRAINT fkj9bca8yvffhyjmbknrabmgupe FOREIGN KEY (attestation_demandestage) REFERENCES schema_formation.td_demandestage(demande_id);


--
-- Name: tp_typedemandecourrier fkjlewmhxd3u6tqv49l7c7t1ncl; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tp_typedemandecourrier
    ADD CONSTRAINT fkjlewmhxd3u6tqv49l7c7t1ncl FOREIGN KEY (typedemcou_division) REFERENCES schema_utilisateur.tp_division(div_id);


--
-- Name: td_formation fkjlxfcyr1v5ndp6bvay2payfj2; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_formation
    ADD CONSTRAINT fkjlxfcyr1v5ndp6bvay2payfj2 FOREIGN KEY (theme_formation_id) REFERENCES schema_formation.td_themeformation(id);


--
-- Name: td_fichiercanditure fkkkinph21ayfnbluom9y82wduh; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_fichiercanditure
    ADD CONSTRAINT fkkkinph21ayfnbluom9y82wduh FOREIGN KEY (formation_id) REFERENCES schema_formation.td_formation(id);


--
-- Name: td_pvexamen fkkkkyxgd7bqb5qdbmtn96fdkmg; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_pvexamen
    ADD CONSTRAINT fkkkkyxgd7bqb5qdbmtn96fdkmg FOREIGN KEY (formation_id) REFERENCES schema_formation.td_formation(id);


--
-- Name: td_courrier fklyn5n546efj94k39nbk9v8f9o; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_courrier
    ADD CONSTRAINT fklyn5n546efj94k39nbk9v8f9o FOREIGN KEY (courrier_direction) REFERENCES schema_utilisateur.tp_direction(dir_id);


--
-- Name: td_traitementexpressiondebesoin fkmt78xr9ivxjsjfbkqglkdmwcy; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_traitementexpressiondebesoin
    ADD CONSTRAINT fkmt78xr9ivxjsjfbkqglkdmwcy FOREIGN KEY (traitexp_statutexpressiondebesoin) REFERENCES schema_formation.tp_statutexpressiondebesoin(stexp_id);


--
-- Name: td_participant fkmuse4qu9704y7no585tcv5p3a; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_participant
    ADD CONSTRAINT fkmuse4qu9704y7no585tcv5p3a FOREIGN KEY (formation_id) REFERENCES schema_formation.td_formation(id);


--
-- Name: tr_theme_formation_profile fkn0x1ijdmdah9259jsv8vt7qd6; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.tr_theme_formation_profile
    ADD CONSTRAINT fkn0x1ijdmdah9259jsv8vt7qd6 FOREIGN KEY (profile_id) REFERENCES schema_utilisateur.tp_profile(pro_id);


--
-- Name: td_avisdemandestage fknhlh54mkeqxkojbjtmgemu3m5; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_avisdemandestage
    ADD CONSTRAINT fknhlh54mkeqxkojbjtmgemu3m5 FOREIGN KEY (avis_centrallevel) REFERENCES schema_utilisateur.td_centrallevel(id);


--
-- Name: td_rapportstage fkni3gh5sr8i01827bmptcvaewn; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_rapportstage
    ADD CONSTRAINT fkni3gh5sr8i01827bmptcvaewn FOREIGN KEY (rapport_demandestage) REFERENCES schema_formation.td_demandestage(demande_id);


--
-- Name: td_attestationstage fknwoojf05v7r9iovgk1vaasae4; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_attestationstage
    ADD CONSTRAINT fknwoojf05v7r9iovgk1vaasae4 FOREIGN KEY (piecesjoint_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_themeformation fkohcb8pc580qodsmnlk0pnky2i; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_themeformation
    ADD CONSTRAINT fkohcb8pc580qodsmnlk0pnky2i FOREIGN KEY (id_plan_formation) REFERENCES schema_formation.td_planformation(id);


--
-- Name: td_courrier fkoi2jggsprwuumdswxxtnbshr6; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_courrier
    ADD CONSTRAINT fkoi2jggsprwuumdswxxtnbshr6 FOREIGN KEY (courrier_division) REFERENCES schema_utilisateur.tp_division(div_id);


--
-- Name: td_session fkojw5u5h66829jpujrq791k6bc; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_session
    ADD CONSTRAINT fkojw5u5h66829jpujrq791k6bc FOREIGN KEY (rapport_file_id) REFERENCES schema_utilisateur.td_file(id);


--
-- Name: td_courrier fkpduukxi29qu08khd2uld8g6ir; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_courrier
    ADD CONSTRAINT fkpduukxi29qu08khd2uld8g6ir FOREIGN KEY (courrier_centrallevel) REFERENCES schema_utilisateur.td_centrallevel(id);


--
-- Name: td_expressiondebesoin fkpgdi1c8oqm51i1ndav0ndgvon; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_expressiondebesoin
    ADD CONSTRAINT fkpgdi1c8oqm51i1ndav0ndgvon FOREIGN KEY (exp_campagne) REFERENCES schema_formation.td_campagne(cam_id);


--
-- Name: td_demandestage fkpj4pn93txudjsd8etdc0ws3k8; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_demandestage
    ADD CONSTRAINT fkpj4pn93txudjsd8etdc0ws3k8 FOREIGN KEY (demande_direction) REFERENCES schema_utilisateur.tp_direction(dir_id);


--
-- Name: td_formation fkpkh08jys0bwnnbcymhqywp6ni; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_formation
    ADD CONSTRAINT fkpkh08jys0bwnnbcymhqywp6ni FOREIGN KEY (statut_formation_id) REFERENCES schema_formation.td_statut_formation(id);


--
-- Name: td_attestationstage fkppq991n8ef3elng3uxmv5m2dt; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_attestationstage
    ADD CONSTRAINT fkppq991n8ef3elng3uxmv5m2dt FOREIGN KEY (attestation_utilisateur) REFERENCES schema_utilisateur.td_centrallevel(id);


--
-- Name: td_courrier fkqpkssodysmphgg429scpfxd79; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_courrier
    ADD CONSTRAINT fkqpkssodysmphgg429scpfxd79 FOREIGN KEY (courrier_typedemande) REFERENCES schema_formation.tp_typedemandecourrier(typedemcou_id);


--
-- Name: td_demandestage fkqwhdf7loupan2n6f9sjvy0l2k; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_demandestage
    ADD CONSTRAINT fkqwhdf7loupan2n6f9sjvy0l2k FOREIGN KEY (demande_status) REFERENCES schema_formation.tp_statutdemandestage(statudeman_id);


--
-- Name: td_demandestage fkr4imm6hqnlp6yfvac06dtrb2h; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_demandestage
    ADD CONSTRAINT fkr4imm6hqnlp6yfvac06dtrb2h FOREIGN KEY (demande_division) REFERENCES schema_utilisateur.tp_division(div_id);


--
-- Name: td_tableau_suivi fkrw08vh2s9ku8klhqd6ssiq0pl; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_tableau_suivi
    ADD CONSTRAINT fkrw08vh2s9ku8klhqd6ssiq0pl FOREIGN KEY (formation_id) REFERENCES schema_formation.td_formation(id);


--
-- Name: td_planformation fksak38aguhsifgeiard8vnpp80; Type: FK CONSTRAINT; Schema: schema_formation; Owner: -
--

ALTER TABLE ONLY schema_formation.td_planformation
    ADD CONSTRAINT fksak38aguhsifgeiard8vnpp80 FOREIGN KEY (statut_plan_formation_id) REFERENCES schema_formation.td_statut_planformation(id);


--
-- Name: td_subaction fk65f4k0essyqisj0j2sec44nx4; Type: FK CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_subaction
    ADD CONSTRAINT fk65f4k0essyqisj0j2sec44nx4 FOREIGN KEY (sub_action_mode_id) REFERENCES schema_pta.td_modecalcul(id);


--
-- Name: td_plandetravail fk67ccbxpjv5cbqy8cb84j8sgv1; Type: FK CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_plandetravail
    ADD CONSTRAINT fk67ccbxpjv5cbqy8cb84j8sgv1 FOREIGN KEY (pta_inial_pta_id) REFERENCES schema_pta.td_initialpta(id);


--
-- Name: td_subaction fk6nehr3dm7rtobuk6xvhwj9owg; Type: FK CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_subaction
    ADD CONSTRAINT fk6nehr3dm7rtobuk6xvhwj9owg FOREIGN KEY (sub_action_result_id) REFERENCES schema_pta.td_resultatpta(id);


--
-- Name: td_modecalcul fk6y482uje1b3882m4ubjg5gfjv; Type: FK CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_modecalcul
    ADD CONSTRAINT fk6y482uje1b3882m4ubjg5gfjv FOREIGN KEY (result_id) REFERENCES schema_pta.td_resultatpta(id);


--
-- Name: td_initialpta fk7k9nr598ws8h5xoe7veq5fa3j; Type: FK CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_initialpta
    ADD CONSTRAINT fk7k9nr598ws8h5xoe7veq5fa3j FOREIGN KEY (init_pta_direction_id) REFERENCES schema_utilisateur.tp_direction(dir_id);


--
-- Name: td_traitementparametre fk7m4m72ot7ah4rs3otkddlif78; Type: FK CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_traitementparametre
    ADD CONSTRAINT fk7m4m72ot7ah4rs3otkddlif78 FOREIGN KEY (traitparam_statutcourrier) REFERENCES schema_pta.tp_statutparametre(stuparam_id);


--
-- Name: td_actionpta fkfqo2nwcglqsly8pmi6b3gxsw4; Type: FK CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_actionpta
    ADD CONSTRAINT fkfqo2nwcglqsly8pmi6b3gxsw4 FOREIGN KEY (pta_action_id) REFERENCES schema_pta.td_plandetravail(id);


--
-- Name: td_resultatpta fkm5kqgvu2s6w9bg3i716q1ay3k; Type: FK CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_resultatpta
    ADD CONSTRAINT fkm5kqgvu2s6w9bg3i716q1ay3k FOREIGN KEY (result_act_pta_id) REFERENCES schema_pta.td_actionpta(id);


--
-- Name: td_traitementparametre fkp9gls63rkjlryuqt37nakl03p; Type: FK CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_traitementparametre
    ADD CONSTRAINT fkp9gls63rkjlryuqt37nakl03p FOREIGN KEY (traitparam_parametre) REFERENCES schema_pta.td_parametre(param_id);


--
-- Name: td_subaction fkrn3mlgpm4ecnlhddw6c580f52; Type: FK CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_subaction
    ADD CONSTRAINT fkrn3mlgpm4ecnlhddw6c580f52 FOREIGN KEY (sub_action_indicateur_id) REFERENCES schema_pta.td_parametre(param_id);


--
-- Name: td_subaction fkt166bv5ji2x4utj2xfvtrobef; Type: FK CONSTRAINT; Schema: schema_pta; Owner: -
--

ALTER TABLE ONLY schema_pta.td_subaction
    ADD CONSTRAINT fkt166bv5ji2x4utj2xfvtrobef FOREIGN KEY (sub_action_report_id) REFERENCES schema_pta.td_reportrealisation(id);


--
-- Name: tp_ief fk10hodrm84p6p1elyo4fwkhuyb; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_ief
    ADD CONSTRAINT fk10hodrm84p6p1elyo4fwkhuyb FOREIGN KEY (ia_id) REFERENCES schema_utilisateur.tp_ia(ia_id);


--
-- Name: td_archived_utilisateur fk18o2eebcbkc9owvljcd44jw7t; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fk18o2eebcbkc9owvljcd44jw7t FOREIGN KEY (division_id) REFERENCES schema_utilisateur.tp_division(div_id);


--
-- Name: tp_service fk265wd714rh7so4jlhrlfpi9ar; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_service
    ADD CONSTRAINT fk265wd714rh7so4jlhrlfpi9ar FOREIGN KEY (direction_id) REFERENCES schema_utilisateur.tp_direction(dir_id);


--
-- Name: td_archived_utilisateur fk2b2v1uuqv6beu1anddqbe2ei1; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fk2b2v1uuqv6beu1anddqbe2ei1 FOREIGN KEY (dip_type_poste_id) REFERENCES schema_utilisateur.tp_type_poste(type_poste_id);


--
-- Name: td_archived_utilisateur fk2m0fideqq6qbub9h2yq8es9tw; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fk2m0fideqq6qbub9h2yq8es9tw FOREIGN KEY (direction_id) REFERENCES schema_utilisateur.tp_direction(dir_id);


--
-- Name: tp_bureau fk2ya4vcc14cq65613ss56mpsi4; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_bureau
    ADD CONSTRAINT fk2ya4vcc14cq65613ss56mpsi4 FOREIGN KEY (division_id) REFERENCES schema_utilisateur.tp_division(div_id);


--
-- Name: tp_typeetablissement fk3hsaq35qvuf0djxj9egwvpajk; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_typeetablissement
    ADD CONSTRAINT fk3hsaq35qvuf0djxj9egwvpajk FOREIGN KEY (typesystemeens_id) REFERENCES schema_utilisateur.tp_typesystemeenseignement(typesystemeens_id);


--
-- Name: tp_profile_menu_child fk46tso5gld1362so4fothjqbab; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_profile_menu_child
    ADD CONSTRAINT fk46tso5gld1362so4fothjqbab FOREIGN KEY (men_id) REFERENCES schema_utilisateur.tp_menu(menu_id);


--
-- Name: td_archived_utilisateur fk4bqmg3jqq6o7cien218lolder; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fk4bqmg3jqq6o7cien218lolder FOREIGN KEY (ief_id) REFERENCES schema_utilisateur.tp_ief(seq_ief);


--
-- Name: tp_eefministere fk4gnjml79a3g6k0pikqd8h3d7p; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_eefministere
    ADD CONSTRAINT fk4gnjml79a3g6k0pikqd8h3d7p FOREIGN KEY (eefmi_region_id) REFERENCES schema_utilisateur.tp_region(reg_id);


--
-- Name: tp_specialiteeef fk4xtnfxl66om0gb2o5hlsa6yk0; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_specialiteeef
    ADD CONSTRAINT fk4xtnfxl66om0gb2o5hlsa6yk0 FOREIGN KEY (speciality_id) REFERENCES schema_utilisateur.tp_speciality(spe_id);


--
-- Name: tp_direction fk5f3n9st753h8k9302j7k8rpxd; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_direction
    ADD CONSTRAINT fk5f3n9st753h8k9302j7k8rpxd FOREIGN KEY (region_id) REFERENCES schema_utilisateur.tp_region(reg_id);


--
-- Name: td_deconcentratedlevel fk5fa31r1fk97r9rl8kq5w2wgs6; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fk5fa31r1fk97r9rl8kq5w2wgs6 FOREIGN KEY (ief_id) REFERENCES schema_utilisateur.tp_ief(seq_ief);


--
-- Name: td_centrallevel fk5pk3r8qt04unrxo99vplm225h; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fk5pk3r8qt04unrxo99vplm225h FOREIGN KEY (division_id) REFERENCES schema_utilisateur.tp_division(div_id);


--
-- Name: td_archived_utilisateur fk609xx7kwknfxxt92iwpowif1y; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fk609xx7kwknfxxt92iwpowif1y FOREIGN KEY (bureau_id) REFERENCES schema_utilisateur.tp_bureau(bur_id);


--
-- Name: td_archived_utilisateur fk62f67b0osgdt23l1gm6y4cxxj; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fk62f67b0osgdt23l1gm6y4cxxj FOREIGN KEY (dip_prof_id) REFERENCES schema_utilisateur.tp_diplome_prof(dip_prof_id);


--
-- Name: td_archived_utilisateur fk6nfplltlsqd8lyawngl650whv; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fk6nfplltlsqd8lyawngl650whv FOREIGN KEY (region_id) REFERENCES schema_utilisateur.tp_region(reg_id);


--
-- Name: td_file fk6r5v3458t3lpylmwvnwd93lkp; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_file
    ADD CONSTRAINT fk6r5v3458t3lpylmwvnwd93lkp FOREIGN KEY (mutation_id) REFERENCES schema_carriere.td_mutation(id);


--
-- Name: tp_efspeciality fk6tfds4nk43k271ghgu7xt2ns5; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_efspeciality
    ADD CONSTRAINT fk6tfds4nk43k271ghgu7xt2ns5 FOREIGN KEY (eef_id) REFERENCES schema_utilisateur.tp_eef(eef_id);


--
-- Name: tp_division fk6uxm69opwcqab6012cdnhyc2m; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_division
    ADD CONSTRAINT fk6uxm69opwcqab6012cdnhyc2m FOREIGN KEY (sub_action_division_id) REFERENCES schema_pta.td_modecalcul(id);


--
-- Name: tr_men_sous_menu fk803s67fjqaxg8stf0mttqjakb; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tr_men_sous_menu
    ADD CONSTRAINT fk803s67fjqaxg8stf0mttqjakb FOREIGN KEY (men_id) REFERENCES schema_utilisateur.tp_menu(menu_id);


--
-- Name: td_parametre_corpsgrade fk928ll2ctq2d5ved8o475bju6d; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_parametre_corpsgrade
    ADD CONSTRAINT fk928ll2ctq2d5ved8o475bju6d FOREIGN KEY (speciality_id) REFERENCES schema_utilisateur.tp_speciality(spe_id);


--
-- Name: td_usermanager fk_43puq5a25squ27nhpraob3np1; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_usermanager
    ADD CONSTRAINT fk_43puq5a25squ27nhpraob3np1 FOREIGN KEY (dip_prof_id) REFERENCES schema_utilisateur.tp_diplome_prof(dip_prof_id);


--
-- Name: td_deconcentratedlevel fk_479kfrvtd568lwvhb3t1flq5e; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fk_479kfrvtd568lwvhb3t1flq5e FOREIGN KEY (fonction_id) REFERENCES schema_utilisateur.tp_fonction(fon_id);


--
-- Name: td_centrallevel fk_529x18f4ofl1srvd4qvq6lot7; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fk_529x18f4ofl1srvd4qvq6lot7 FOREIGN KEY (dip_prof_id) REFERENCES schema_utilisateur.tp_diplome_prof(dip_prof_id);


--
-- Name: td_deconcentratedlevel fk_5dxlu3209h2rk1ku8m6hcq46d; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fk_5dxlu3209h2rk1ku8m6hcq46d FOREIGN KEY (dip_type_poste_id) REFERENCES schema_utilisateur.tp_type_poste(type_poste_id);


--
-- Name: td_centrallevel fk_67lhyb5nr5bvj9s88y7v8y0hh; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fk_67lhyb5nr5bvj9s88y7v8y0hh FOREIGN KEY (region_id) REFERENCES schema_utilisateur.tp_region(reg_id);


--
-- Name: td_centrallevel fk_6xno4nt426j5asyck3sono6wg; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fk_6xno4nt426j5asyck3sono6wg FOREIGN KEY (dip_aca_id) REFERENCES schema_utilisateur.tp_diplome_aca(dip_aca_id);


--
-- Name: td_usermanager fk_83y9o8qvvhnoydmcmnc9rcnhs; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_usermanager
    ADD CONSTRAINT fk_83y9o8qvvhnoydmcmnc9rcnhs FOREIGN KEY (region_id) REFERENCES schema_utilisateur.tp_region(reg_id);


--
-- Name: td_centrallevel fk_8xqfdgpv7t0xtllt7ix6goef8; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fk_8xqfdgpv7t0xtllt7ix6goef8 FOREIGN KEY (type_mat_id) REFERENCES schema_utilisateur.tp_type_matricule(type_mat_id);


--
-- Name: td_deconcentratedlevel fk_9jytme760bi84xde64kabk80w; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fk_9jytme760bi84xde64kabk80w FOREIGN KEY (type_mat_id) REFERENCES schema_utilisateur.tp_type_matricule(type_mat_id);


--
-- Name: td_usermanager fk_a9dw766bmohk85456w3rvdeu6; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_usermanager
    ADD CONSTRAINT fk_a9dw766bmohk85456w3rvdeu6 FOREIGN KEY (grade_id) REFERENCES schema_utilisateur.tp_grade(grade_id);


--
-- Name: td_deconcentratedlevel fk_albh1x37jhkx2ch6374wpsfek; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fk_albh1x37jhkx2ch6374wpsfek FOREIGN KEY (speciality_id) REFERENCES schema_utilisateur.tp_speciality(spe_id);


--
-- Name: td_usermanager fk_d15b327fyfg1s8hiti2v0sut4; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_usermanager
    ADD CONSTRAINT fk_d15b327fyfg1s8hiti2v0sut4 FOREIGN KEY (dip_aca_id) REFERENCES schema_utilisateur.tp_diplome_aca(dip_aca_id);


--
-- Name: td_deconcentratedlevel fk_drlknyqyuej2fdpo3et8cidjo; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fk_drlknyqyuej2fdpo3et8cidjo FOREIGN KEY (dip_ped_id) REFERENCES schema_utilisateur.tp_diplome_ped(dip_ped_id);


--
-- Name: td_deconcentratedlevel fk_dvrnjmk7xrj9kbt0lt9w5bvl5; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fk_dvrnjmk7xrj9kbt0lt9w5bvl5 FOREIGN KEY (corp_id) REFERENCES schema_utilisateur.tp_corps(cor_id);


--
-- Name: td_usermanager fk_gl42i09raai6oly0vvfnkdb9r; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_usermanager
    ADD CONSTRAINT fk_gl42i09raai6oly0vvfnkdb9r FOREIGN KEY (dip_type_poste_id) REFERENCES schema_utilisateur.tp_type_poste(type_poste_id);


--
-- Name: td_usermanager fk_gvna7i5jwdxtddiic55ylgmey; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_usermanager
    ADD CONSTRAINT fk_gvna7i5jwdxtddiic55ylgmey FOREIGN KEY (dip_ped_id) REFERENCES schema_utilisateur.tp_diplome_ped(dip_ped_id);


--
-- Name: td_usermanager fk_gxvru32610rpa3mh3m2rbt99p; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_usermanager
    ADD CONSTRAINT fk_gxvru32610rpa3mh3m2rbt99p FOREIGN KEY (speciality_id) REFERENCES schema_utilisateur.tp_speciality(spe_id);


--
-- Name: td_deconcentratedlevel fk_ici21g4qa3k6oayh884ypx475; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fk_ici21g4qa3k6oayh884ypx475 FOREIGN KEY (dip_prof_id) REFERENCES schema_utilisateur.tp_diplome_prof(dip_prof_id);


--
-- Name: td_usermanager fk_ihr9knt1g3r0tx8lbcdmdvvbj; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_usermanager
    ADD CONSTRAINT fk_ihr9knt1g3r0tx8lbcdmdvvbj FOREIGN KEY (corp_id) REFERENCES schema_utilisateur.tp_corps(cor_id);


--
-- Name: td_centrallevel fk_jqunk3nrr6hl9svavvxl7flwg; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fk_jqunk3nrr6hl9svavvxl7flwg FOREIGN KEY (speciality_id) REFERENCES schema_utilisateur.tp_speciality(spe_id);


--
-- Name: td_usermanager fk_kb35qj7nkiypsgtevjv6unrc0; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_usermanager
    ADD CONSTRAINT fk_kb35qj7nkiypsgtevjv6unrc0 FOREIGN KEY (fonction_id) REFERENCES schema_utilisateur.tp_fonction(fon_id);


--
-- Name: td_centrallevel fk_lvux685jni9g8h3stqay9gsci; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fk_lvux685jni9g8h3stqay9gsci FOREIGN KEY (grade_id) REFERENCES schema_utilisateur.tp_grade(grade_id);


--
-- Name: td_centrallevel fk_m56mxn11ode1oidyo457rcbh9; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fk_m56mxn11ode1oidyo457rcbh9 FOREIGN KEY (dip_ped_id) REFERENCES schema_utilisateur.tp_diplome_ped(dip_ped_id);


--
-- Name: td_deconcentratedlevel fk_n3nfhlx0djsbo59hy1r21820t; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fk_n3nfhlx0djsbo59hy1r21820t FOREIGN KEY (dip_aca_id) REFERENCES schema_utilisateur.tp_diplome_aca(dip_aca_id);


--
-- Name: td_centrallevel fk_no5n45l80k129vb9k8ucs922d; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fk_no5n45l80k129vb9k8ucs922d FOREIGN KEY (fonction_id) REFERENCES schema_utilisateur.tp_fonction(fon_id);


--
-- Name: td_deconcentratedlevel fk_oepuicxa65qr6oiug2i4d3hbi; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fk_oepuicxa65qr6oiug2i4d3hbi FOREIGN KEY (grade_id) REFERENCES schema_utilisateur.tp_grade(grade_id);


--
-- Name: td_centrallevel fk_rauh5uvpbtldpw5bg0m0u64mp; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fk_rauh5uvpbtldpw5bg0m0u64mp FOREIGN KEY (dip_type_poste_id) REFERENCES schema_utilisateur.tp_type_poste(type_poste_id);


--
-- Name: td_centrallevel fk_rl0r6i4lahvn99ktdql8efq14; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fk_rl0r6i4lahvn99ktdql8efq14 FOREIGN KEY (corp_id) REFERENCES schema_utilisateur.tp_corps(cor_id);


--
-- Name: td_usermanager fk_spyllu1d7sb7i5n2ui1jgrwr6; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_usermanager
    ADD CONSTRAINT fk_spyllu1d7sb7i5n2ui1jgrwr6 FOREIGN KEY (type_mat_id) REFERENCES schema_utilisateur.tp_type_matricule(type_mat_id);


--
-- Name: td_deconcentratedlevel fk_srdgri6l7bo4aid9u6xv32t5h; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fk_srdgri6l7bo4aid9u6xv32t5h FOREIGN KEY (region_id) REFERENCES schema_utilisateur.tp_region(reg_id);


--
-- Name: tr_speciality_etablissement fka5rllcgwy3tu4ixobanoceh1i; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tr_speciality_etablissement
    ADD CONSTRAINT fka5rllcgwy3tu4ixobanoceh1i FOREIGN KEY (etablissement_id) REFERENCES schema_utilisateur.tp_typeetablissement(eta_id);


--
-- Name: td_archived_utilisateur fkbcnku881bkdgj74xvl6t5joio; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fkbcnku881bkdgj74xvl6t5joio FOREIGN KEY (fonction_id) REFERENCES schema_utilisateur.tp_fonction(fon_id);


--
-- Name: td_archived_utilisateur fkcnyrysiivmk4wxfct0dor3or; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fkcnyrysiivmk4wxfct0dor3or FOREIGN KEY (speciality_id) REFERENCES schema_utilisateur.tp_speciality(spe_id);


--
-- Name: tp_cfp fkdmqc26dntb5lenkm9y0yv2xe; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_cfp
    ADD CONSTRAINT fkdmqc26dntb5lenkm9y0yv2xe FOREIGN KEY (ief_id) REFERENCES schema_utilisateur.tp_ief(seq_ief);


--
-- Name: td_parametre_corpsgrade_pk fkdqssd6akl2o89dl5klc1uslg9; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_parametre_corpsgrade_pk
    ADD CONSTRAINT fkdqssd6akl2o89dl5klc1uslg9 FOREIGN KEY (speciality_id) REFERENCES schema_utilisateur.td_parametre_corpsgrade(params_cg_id);


--
-- Name: tp_ia fkdrbbtd9ei4n9ul8we3491q8ju; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_ia
    ADD CONSTRAINT fkdrbbtd9ei4n9ul8we3491q8ju FOREIGN KEY (region_id) REFERENCES schema_utilisateur.tp_region(reg_id);


--
-- Name: td_parametre_corpsgrade fkeegrf5heb1g2jemfyq6am9bnh; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_parametre_corpsgrade
    ADD CONSTRAINT fkeegrf5heb1g2jemfyq6am9bnh FOREIGN KEY (corps_grade_id) REFERENCES schema_utilisateur.tp_corps(cor_id);


--
-- Name: tp_profile_menu_child fkeemauy6la25kb442aeshoi6s0; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_profile_menu_child
    ADD CONSTRAINT fkeemauy6la25kb442aeshoi6s0 FOREIGN KEY (pro_id) REFERENCES schema_utilisateur.tp_profile(pro_id);


--
-- Name: tr_speciality_etablissement fkekb5d1eed2giyfnks7lsytvq1; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tr_speciality_etablissement
    ADD CONSTRAINT fkekb5d1eed2giyfnks7lsytvq1 FOREIGN KEY (speciality_id) REFERENCES schema_utilisateur.tp_speciality(spe_id);


--
-- Name: td_centrallevel fkelf97f7c1sb0gkpvnv6vm5xgn; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fkelf97f7c1sb0gkpvnv6vm5xgn FOREIGN KEY (service_id) REFERENCES schema_utilisateur.tp_service(ser_id);


--
-- Name: tp_typeetablissement fkevmw2pvt9efemtl99ofja75h9; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_typeetablissement
    ADD CONSTRAINT fkevmw2pvt9efemtl99ofja75h9 FOREIGN KEY (ia_id) REFERENCES schema_utilisateur.tp_ia(ia_id);


--
-- Name: td_archived_utilisateur fkf09e7xylkmpns7hsoj3dblcmg; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fkf09e7xylkmpns7hsoj3dblcmg FOREIGN KEY (grade_id) REFERENCES schema_utilisateur.tp_grade(grade_id);


--
-- Name: tp_grade fkf0vwg5g75u1s9nrhxih7mug1e; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_grade
    ADD CONSTRAINT fkf0vwg5g75u1s9nrhxih7mug1e FOREIGN KEY (corps_id) REFERENCES schema_utilisateur.tp_corps(cor_id);


--
-- Name: tp_diplome_ped fkffctd540iu93vihtdk7ditalj; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_diplome_ped
    ADD CONSTRAINT fkffctd540iu93vihtdk7ditalj FOREIGN KEY (diplomes_dip_id) REFERENCES schema_utilisateur.td_diplome(dip_id);


--
-- Name: tr_men_sous_menu fkfu8frbjk9ay6t6q8bwdwrubeh; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tr_men_sous_menu
    ADD CONSTRAINT fkfu8frbjk9ay6t6q8bwdwrubeh FOREIGN KEY (smn_id) REFERENCES schema_utilisateur.tp_menu(menu_id);


--
-- Name: td_file fkfygrmplxfmx1ad6uwcvk4flkv; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_file
    ADD CONSTRAINT fkfygrmplxfmx1ad6uwcvk4flkv FOREIGN KEY (acte_id) REFERENCES schema_carriere.td_acte(acte_id);


--
-- Name: tr_user_profile fkgak6a8vo4gevyoroov6yq3jxy; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tr_user_profile
    ADD CONSTRAINT fkgak6a8vo4gevyoroov6yq3jxy FOREIGN KEY (profile_id) REFERENCES schema_utilisateur.tp_profile(pro_id);


--
-- Name: td_archived_utilisateur fkgn8ci5xn3pa6nblbacpji1ag; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fkgn8ci5xn3pa6nblbacpji1ag FOREIGN KEY (type_mat_id) REFERENCES schema_utilisateur.tp_type_matricule(type_mat_id);


--
-- Name: tp_division fkgox4xqmohtb2unqus1bs99qka; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_division
    ADD CONSTRAINT fkgox4xqmohtb2unqus1bs99qka FOREIGN KEY (direction_id) REFERENCES schema_utilisateur.tp_direction(dir_id);


--
-- Name: tp_typeetablissement fkgxrao4bs0eycs7un6aptiutxo; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_typeetablissement
    ADD CONSTRAINT fkgxrao4bs0eycs7un6aptiutxo FOREIGN KEY (typesystemeens_id) REFERENCES schema_utilisateur.tp_type_systeme_enseignement(typesystemeens_id);


--
-- Name: td_centrallevel fkh5x4cr67wmoepeagjmsesnh7u; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fkh5x4cr67wmoepeagjmsesnh7u FOREIGN KEY (direction_id) REFERENCES schema_utilisateur.tp_direction(dir_id);


--
-- Name: td_archived_utilisateur fkhwwenfscb3af6aco6drspx3e2; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fkhwwenfscb3af6aco6drspx3e2 FOREIGN KEY (corp_id) REFERENCES schema_utilisateur.tp_corps(cor_id);


--
-- Name: tp_typeetablissement fkinnqjfcs25ltqcd49pm32w4j0; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_typeetablissement
    ADD CONSTRAINT fkinnqjfcs25ltqcd49pm32w4j0 FOREIGN KEY (ief_id) REFERENCES schema_utilisateur.tp_ief(seq_ief);


--
-- Name: td_diplome fkixmefyw0jjkwibj8sbau039vc; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_diplome
    ADD CONSTRAINT fkixmefyw0jjkwibj8sbau039vc FOREIGN KEY (type_diplome_id) REFERENCES schema_utilisateur.tp_type_diplome(type_dip_id);


--
-- Name: td_file fkj4o9ji4sq9hrgt6j1frk33wq0; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_file
    ADD CONSTRAINT fkj4o9ji4sq9hrgt6j1frk33wq0 FOREIGN KEY (cam_piecejoint) REFERENCES schema_formation.td_campagne(cam_id);


--
-- Name: td_archived_utilisateur fkj76dtpq8gcxb705x8h4wop6jj; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fkj76dtpq8gcxb705x8h4wop6jj FOREIGN KEY (service_id) REFERENCES schema_utilisateur.tp_service(ser_id);


--
-- Name: td_file fkjnsa37q2x9mwuqqthc4wsy1mw; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_file
    ADD CONSTRAINT fkjnsa37q2x9mwuqqthc4wsy1mw FOREIGN KEY (report_files_id) REFERENCES schema_pta.td_reportrealisation(id);


--
-- Name: td_deconcentratedlevel fkjq23gkth8q1ajujga0xavbgm; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fkjq23gkth8q1ajujga0xavbgm FOREIGN KEY (etablissement_id) REFERENCES schema_utilisateur.tp_typeetablissement(eta_id);


--
-- Name: td_archived_utilisateur fkkj3exi7gleyj5i8ygoait6erx; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fkkj3exi7gleyj5i8ygoait6erx FOREIGN KEY (etablissement_id) REFERENCES schema_utilisateur.tp_typeetablissement(eta_id);


--
-- Name: td_file fkkjb9wcx5sb6j0231bux7en5jx; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_file
    ADD CONSTRAINT fkkjb9wcx5sb6j0231bux7en5jx FOREIGN KEY (pec_piece_id) REFERENCES schema_affairesociale.td_priseencharge(pec_id);


--
-- Name: tp_specialiteeef fkkki0ewf782a8d7t6856mfdr1a; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_specialiteeef
    ADD CONSTRAINT fkkki0ewf782a8d7t6856mfdr1a FOREIGN KEY (etablissement_id) REFERENCES schema_utilisateur.tp_typeetablissement(eta_id);


--
-- Name: td_parametre_corpsgrade_pk fkl54q8eeaqb6p6c9pmdhw08svn; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_parametre_corpsgrade_pk
    ADD CONSTRAINT fkl54q8eeaqb6p6c9pmdhw08svn FOREIGN KEY (grade_id) REFERENCES schema_utilisateur.tp_grade(grade_id);


--
-- Name: td_deconcentratedlevel fkldm1t2p54ekpapugoot42ci3n; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fkldm1t2p54ekpapugoot42ci3n FOREIGN KEY (ia_id) REFERENCES schema_utilisateur.tp_ia(ia_id);


--
-- Name: td_centrallevel fkndfhm2db7va5yf2w4irfcrvfq; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_centrallevel
    ADD CONSTRAINT fkndfhm2db7va5yf2w4irfcrvfq FOREIGN KEY (bureau_id) REFERENCES schema_utilisateur.tp_bureau(bur_id);


--
-- Name: tp_diplome_aca fknfw0gxe90qq5eajstb16b4isi; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_diplome_aca
    ADD CONSTRAINT fknfw0gxe90qq5eajstb16b4isi FOREIGN KEY (diplomes_dip_id) REFERENCES schema_utilisateur.td_diplome(dip_id);


--
-- Name: td_archived_utilisateur fkntmk6pb14eliwso36vl91gjet; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fkntmk6pb14eliwso36vl91gjet FOREIGN KEY (dip_aca_id) REFERENCES schema_utilisateur.tp_diplome_aca(dip_aca_id);


--
-- Name: td_parametre_corpsgrade_pk fkocvgo4x04bf3a5tap5i5bllgq; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_parametre_corpsgrade_pk
    ADD CONSTRAINT fkocvgo4x04bf3a5tap5i5bllgq FOREIGN KEY (type_matricule_id) REFERENCES schema_utilisateur.tp_type_matricule(type_mat_id);


--
-- Name: td_file fkov6bcjv79mrbaytxl0n0bl0xs; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_file
    ADD CONSTRAINT fkov6bcjv79mrbaytxl0n0bl0xs FOREIGN KEY (imp_id) REFERENCES schema_carriere.td_imputationoubulletin(imp_id);


--
-- Name: td_deconcentratedlevel fkpml75p9b4mbohfofcndx9pcdw; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fkpml75p9b4mbohfofcndx9pcdw FOREIGN KEY (structure_id) REFERENCES schema_utilisateur.tp_structure(str_id);


--
-- Name: td_archived_utilisateur fkqcqoycecr6pkax3dfc7hecn7v; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fkqcqoycecr6pkax3dfc7hecn7v FOREIGN KEY (ia_id) REFERENCES schema_utilisateur.tp_ia(ia_id);


--
-- Name: tp_typeetablissement fkr9akfvnrclmggpcapohiv4mt; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_typeetablissement
    ADD CONSTRAINT fkr9akfvnrclmggpcapohiv4mt FOREIGN KEY (typeetablissement_id) REFERENCES schema_utilisateur.tp_etablissement_type(eta_id);


--
-- Name: td_archived_utilisateur fkrblxlate1x0va0pis4iq51ry0; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fkrblxlate1x0va0pis4iq51ry0 FOREIGN KEY (structure_id) REFERENCES schema_utilisateur.tp_structure(str_id);


--
-- Name: td_archived_utilisateur fkrw1ymy1clctepck1v6otjlya4; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_archived_utilisateur
    ADD CONSTRAINT fkrw1ymy1clctepck1v6otjlya4 FOREIGN KEY (dip_ped_id) REFERENCES schema_utilisateur.tp_diplome_ped(dip_ped_id);


--
-- Name: tp_typeetablissement fkscklnqo4iq47njcgy3gkeekxq; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_typeetablissement
    ADD CONSTRAINT fkscklnqo4iq47njcgy3gkeekxq FOREIGN KEY (structure_id) REFERENCES schema_utilisateur.tp_structure(str_id);


--
-- Name: tp_diplome_prof fkse8j39q1lapmoh502klplciov; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_diplome_prof
    ADD CONSTRAINT fkse8j39q1lapmoh502klplciov FOREIGN KEY (diplomes_dip_id) REFERENCES schema_utilisateur.td_diplome(dip_id);


--
-- Name: tp_profile_menu_child fksvjof69nevmy95wydy2oukn07; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.tp_profile_menu_child
    ADD CONSTRAINT fksvjof69nevmy95wydy2oukn07 FOREIGN KEY (smn_id) REFERENCES schema_utilisateur.tp_menu(menu_id);


--
-- Name: td_deconcentratedlevel fktapryao8jcte2k30tl9hxou1r; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.td_deconcentratedlevel
    ADD CONSTRAINT fktapryao8jcte2k30tl9hxou1r FOREIGN KEY (typesystemeens_id) REFERENCES schema_utilisateur.tp_type_systeme_enseignement(typesystemeens_id);


--
-- Name: notification_readers notification_readers_notification_id_fkey; Type: FK CONSTRAINT; Schema: schema_utilisateur; Owner: -
--

ALTER TABLE ONLY schema_utilisateur.notification_readers
    ADD CONSTRAINT notification_readers_notification_id_fkey FOREIGN KEY (notification_id) REFERENCES schema_utilisateur.td_notification(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

\unrestrict SHTYllqaN5duJylxaOA9e3G7bvFC8PQd1zqFLmKA0F3bZdILnWDDEAkAyaaBCEt

