--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

-- Started on 2022-11-02 15:47:49

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 3320 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 33625)
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accounts (
    user_id bigint NOT NULL,
    balance double precision
);


ALTER TABLE public.accounts OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 33624)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 33630)
-- Name: transfer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transfer (
    op_id bigint NOT NULL,
    amount double precision,
    date timestamp without time zone,
    type character varying(255),
    user_id_user_id bigint
);


ALTER TABLE public.transfer OWNER TO postgres;

--
-- TOC entry 3313 (class 0 OID 33625)
-- Dependencies: 210
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.accounts (user_id, balance) FROM stdin;
1	111
2	222
3	333
4	444
5	555
\.


--
-- TOC entry 3314 (class 0 OID 33630)
-- Dependencies: 211
-- Data for Name: transfer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transfer (op_id, amount, date, type, user_id_user_id) FROM stdin;
\.


--
-- TOC entry 3321 (class 0 OID 0)
-- Dependencies: 209
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 5, true);


--
-- TOC entry 3169 (class 2606 OID 33629)
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3171 (class 2606 OID 33634)
-- Name: transfer transfer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transfer
    ADD CONSTRAINT transfer_pkey PRIMARY KEY (op_id);


--
-- TOC entry 3172 (class 2606 OID 33635)
-- Name: transfer fkctghxx4g3of6346r6cxel2f45; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transfer
    ADD CONSTRAINT fkctghxx4g3of6346r6cxel2f45 FOREIGN KEY (user_id_user_id) REFERENCES public.accounts(user_id);


-- Completed on 2022-11-02 15:47:49

--
-- PostgreSQL database dump complete
--

