--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

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
-- Name: epoll; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE epoll WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';


ALTER DATABASE epoll OWNER TO postgres;

\connect epoll

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
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: followed; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.followed (
    followed_id integer NOT NULL,
    follower_id integer NOT NULL
);


ALTER TABLE public.followed OWNER TO postgres;

--
-- Name: moderatorrequest; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.moderatorrequest (
    id integer NOT NULL,
    createdat timestamp without time zone,
    reason character varying(255) NOT NULL,
    status character varying(255),
    acceptor_id integer,
    createdby_id integer
);


ALTER TABLE public.moderatorrequest OWNER TO postgres;

--
-- Name: photo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.photo (
    id integer NOT NULL,
    url character varying(255) NOT NULL,
    post_id integer NOT NULL
);


ALTER TABLE public.photo OWNER TO postgres;

--
-- Name: poll; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.poll (
    id integer NOT NULL,
    frozen boolean NOT NULL,
    post_id integer NOT NULL
);


ALTER TABLE public.poll OWNER TO postgres;

--
-- Name: post; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.post (
    id integer NOT NULL,
    createdat timestamp without time zone,
    text character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    visible boolean,
    createdby_id integer,
    removedby_id integer
);


ALTER TABLE public.post OWNER TO postgres;

--
-- Name: postcomment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.postcomment (
    id integer NOT NULL,
    createdat timestamp without time zone,
    text character varying(255) NOT NULL,
    visible boolean,
    createdby_id integer,
    post_id integer,
    removedby_id integer,
    repliedto_id integer
);


ALTER TABLE public.postcomment OWNER TO postgres;

--
-- Name: sequence; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sequence (
    seq_name character varying(50) NOT NULL,
    seq_count numeric(38,0)
);


ALTER TABLE public.sequence OWNER TO postgres;

--
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    id integer NOT NULL,
    age integer NOT NULL,
    avatarurl character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    phonenumber character varying(255) NOT NULL,
    role character varying(255),
    username character varying(255) NOT NULL,
    visible boolean,
    removedby_id integer
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- Name: variant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.variant (
    id integer NOT NULL,
    createdat timestamp without time zone,
    text character varying(255) NOT NULL,
    visible boolean,
    votes integer NOT NULL,
    createdby_id integer,
    poll_id integer NOT NULL,
    removedby_id integer
);


ALTER TABLE public.variant OWNER TO postgres;

--
-- Name: variantcomment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.variantcomment (
    id integer NOT NULL,
    createdat timestamp without time zone,
    text character varying(255) NOT NULL,
    visible boolean,
    createdby_id integer,
    removedby_id integer,
    repliedto_id integer,
    variant_id integer
);


ALTER TABLE public.variantcomment OWNER TO postgres;

--
-- Name: video; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.video (
    id integer NOT NULL,
    url character varying(255) NOT NULL,
    post_id integer
);


ALTER TABLE public.video OWNER TO postgres;

--
-- Name: voters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.voters (
    poll_id integer NOT NULL,
    voter_id integer NOT NULL
);


ALTER TABLE public.voters OWNER TO postgres;

--
-- Data for Name: followed; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.followed (followed_id, follower_id) VALUES (9, 3);
INSERT INTO public.followed (followed_id, follower_id) VALUES (9, 1);
INSERT INTO public.followed (followed_id, follower_id) VALUES (1, 9);


--
-- Data for Name: moderatorrequest; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.moderatorrequest (id, createdat, reason, status, acceptor_id, createdby_id) VALUES (51, '2022-01-02 15:49:17.899213', 'I wanna be a moderator, pls', 'DECLINED', NULL, 7);
INSERT INTO public.moderatorrequest (id, createdat, reason, status, acceptor_id, createdby_id) VALUES (101, '2022-01-02 16:01:10.084273', 'PLEAAAAAAAAASE', 'ACCEPTED', 9, 7);
INSERT INTO public.moderatorrequest (id, createdat, reason, status, acceptor_id, createdby_id) VALUES (323, '2022-01-02 19:09:32.994361', 'Please, I wanna be a moder', 'ACCEPTED', 9, 3);


--
-- Data for Name: photo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.photo (id, url, post_id) VALUES (108, 'photo1.jpg', 102);
INSERT INTO public.photo (id, url, post_id) VALUES (109, 'photo2.jpg', 102);
INSERT INTO public.photo (id, url, post_id) VALUES (107, 'photo.jpg', 102);
INSERT INTO public.photo (id, url, post_id) VALUES (319, 'string', 313);


--
-- Data for Name: poll; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.poll (id, frozen, post_id) VALUES (103, true, 102);
INSERT INTO public.poll (id, frozen, post_id) VALUES (111, false, 110);
INSERT INTO public.poll (id, frozen, post_id) VALUES (314, true, 313);


--
-- Data for Name: post; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.post (id, createdat, text, title, visible, createdby_id, removedby_id) VALUES (102, '2022-01-02 16:56:59.288565', 'Hi everybody, let''s go', 'title for post', true, 7, NULL);
INSERT INTO public.post (id, createdat, text, title, visible, createdby_id, removedby_id) VALUES (110, '2022-01-02 17:04:17.326546', 'Vote for the best movie', 'The best movie', true, 7, NULL);
INSERT INTO public.post (id, createdat, text, title, visible, createdby_id, removedby_id) VALUES (151, '2022-01-02 17:19:36.77642', 'New content 12', 'New title 21', true, 7, NULL);
INSERT INTO public.post (id, createdat, text, title, visible, createdby_id, removedby_id) VALUES (313, '2022-01-02 18:54:19.385681', 'string', 'string', true, 3, NULL);
INSERT INTO public.post (id, createdat, text, title, visible, createdby_id, removedby_id) VALUES (304, '2022-01-02 18:42:05.041485', 'string string', 'title for title', false, 303, 3);


--
-- Data for Name: postcomment; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.postcomment (id, createdat, text, visible, createdby_id, post_id, removedby_id, repliedto_id) VALUES (257, '2022-01-02 17:31:58.736267', 'Spam spam', true, 6, 110, NULL, 256);
INSERT INTO public.postcomment (id, createdat, text, visible, createdby_id, post_id, removedby_id, repliedto_id) VALUES (258, '2022-01-02 17:32:09.170203', 'Spam 3', true, 6, 110, NULL, 257);
INSERT INTO public.postcomment (id, createdat, text, visible, createdby_id, post_id, removedby_id, repliedto_id) VALUES (256, '2022-01-02 17:31:35.577594', 'spam 2', false, 6, 110, NULL, NULL);
INSERT INTO public.postcomment (id, createdat, text, visible, createdby_id, post_id, removedby_id, repliedto_id) VALUES (309, '2022-01-02 18:45:51.20471', 'Comment for post, hello', true, 303, 304, NULL, NULL);
INSERT INTO public.postcomment (id, createdat, text, visible, createdby_id, post_id, removedby_id, repliedto_id) VALUES (311, '2022-01-02 18:46:51.356681', 'C', true, 303, 304, NULL, 309);
INSERT INTO public.postcomment (id, createdat, text, visible, createdby_id, post_id, removedby_id, repliedto_id) VALUES (312, '2022-01-02 18:47:31.09795', 'G', true, 303, 304, NULL, 309);
INSERT INTO public.postcomment (id, createdat, text, visible, createdby_id, post_id, removedby_id, repliedto_id) VALUES (310, '2022-01-02 18:46:07.56975', 'Comment for for for', false, 303, 304, NULL, NULL);
INSERT INTO public.postcomment (id, createdat, text, visible, createdby_id, post_id, removedby_id, repliedto_id) VALUES (321, '2022-01-02 18:59:29.638204', 'string hello', true, 3, 304, NULL, 309);


--
-- Data for Name: sequence; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.sequence (seq_name, seq_count) VALUES ('SEQ_GEN', 350);


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."user" (id, age, avatarurl, email, password, phonenumber, role, username, visible, removedby_id) VALUES (5, 22, 'https://www.gravatar.com/avatar/default.jpg', 'user3@mail.com', '$2a$10$WwDYmR4CyShDs2u0yZlhnOsg796e7fRwoNZcC7M/Fkt/yK5xZdmXO', '+420111111111', 'USER', 'user3', true, NULL);
INSERT INTO public."user" (id, age, avatarurl, email, password, phonenumber, role, username, visible, removedby_id) VALUES (1, 20, 'https://www.gravatar.com/avatar/default.jpg', 'user1@mail.com', '$2a$10$ZZJgUQEIBaFnODyeqQg02uX0Zho1FT6NrxKmLKhjGrrVCnBWqbSCS', '+420123456789', 'USER', 'user1', true, NULL);
INSERT INTO public."user" (id, age, avatarurl, email, password, phonenumber, role, username, visible, removedby_id) VALUES (8, 51, 'https://www.gravatar.com/avatar/default.jpg', 'moderator2@mail.cz', '$2a$10$vgRpNrr2qqTIR62WDgRyVeIMmU1Y7tbqshJdiOoW0vOuOc6dcaade', '+49 301234567', 'USER', 'moderator2', true, NULL);
INSERT INTO public."user" (id, age, avatarurl, email, password, phonenumber, role, username, visible, removedby_id) VALUES (9, 99, 'https://www.gravatar.com/avatar/default.jpg', 'admin1@mail.cz', '$2a$10$EC68uZT8.BI0HeS46xmMp.026AFF1uqstt4n8t4iWFgcWV8R2iZaG', '+380663657809', 'ADMIN', 'admin1', true, NULL);
INSERT INTO public."user" (id, age, avatarurl, email, password, phonenumber, role, username, visible, removedby_id) VALUES (6, 22, 'https://www.gravatar.com/avatar/default.jpg', 'user4@mail.com', '$2a$10$pnxfiiw5V.UqQZ48AlvsE.hvs6reSAnBrfoCkwVyZ6KQen/O74kDK', '+79008007060', 'USER', 'user4', false, 7);
INSERT INTO public."user" (id, age, avatarurl, email, password, phonenumber, role, username, visible, removedby_id) VALUES (301, 20, 'https://www.gravatar.com/avatar/default.jpg', 'user5@mail.com', '$2a$10$hPhm3LZm9.qrb1uWBZK0h.FFlnwmxJJof16tKMrrGPLpezw.RE.2u', '+420222333444', 'USER', 'user5', true, NULL);
INSERT INTO public."user" (id, age, avatarurl, email, password, phonenumber, role, username, visible, removedby_id) VALUES (302, 30, 'https://www.gravatar.com/avatar/default.jpg', 'moderator3@mail.com', '$2a$10$HPp19s3uohDxbttfKoRfHOUFLj9QAhMNb2wZ7SqNMAEM3uSwFhJoO', '+420444555444', 'USER', 'moderator3', true, NULL);
INSERT INTO public."user" (id, age, avatarurl, email, password, phonenumber, role, username, visible, removedby_id) VALUES (3, 30, 'https://www.gravatar.com/avatar/default.jpg', 'user2@mail.com', '$2a$10$D1q0rkUm7Up5piug7HXzJ.C9KKfkFXcwA5BmL0nqQnPpxijRwsvPO', '+420987654321', 'MODERATOR', 'user2', true, NULL);
INSERT INTO public."user" (id, age, avatarurl, email, password, phonenumber, role, username, visible, removedby_id) VALUES (303, 40, 'https://www.gravatar.com/avatar/default.jpg', 'user8@mail.com', '$2a$10$GyUG0hdNyuJEqj5/nW43cOWwVRWw0.sPHAqzNDYLUnSBTc.smct/e', '+420111000222', 'USER', 'user8', false, 3);
INSERT INTO public."user" (id, age, avatarurl, email, password, phonenumber, role, username, visible, removedby_id) VALUES (7, 25, 'https://www.gravatar.com/avatar/default.jpg', 'moderator1@mail.cz', '$2a$10$kYk1OhZDAlshKboeai7h6.Z0qflRs7qOUj3z5QQ4AOuR5VggzP.XO', '+14255551212', 'MODERATOR', 'moderator1', false, 9);


--
-- Data for Name: variant; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (104, '2022-01-02 16:56:59.288565', 'A', true, 0, 7, 103, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (105, '2022-01-02 16:56:59.288565', 'B', true, 0, 7, 103, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (119, '2022-01-02 17:04:17.326546', 'History of history', true, 0, 7, 111, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (120, '2022-01-02 17:04:17.326546', 'Příběh', true, 0, 7, 111, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (122, '2022-01-02 17:04:17.326546', 'Dobrý den', true, 0, 7, 111, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (121, '2022-01-02 17:04:17.326546', 'Добрый день', true, 0, 7, 111, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (113, '2022-01-02 17:04:17.326546', '1984', true, 0, 7, 111, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (123, '2022-01-02 17:04:17.326546', 'Dobrý den', true, 0, 7, 111, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (116, '2022-01-02 17:04:17.326546', 'Social media', true, 0, 7, 111, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (118, '2022-01-02 17:04:17.326546', 'Sám doma', true, 0, 7, 111, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (114, '2022-01-02 17:04:17.326546', 'Transformers', true, 0, 7, 111, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (115, '2022-01-02 17:04:17.326546', 'Superman', true, 0, 7, 111, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (112, '2022-01-02 17:04:17.326546', 'Spider-man', false, 0, 7, 111, 7);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (317, '2022-01-02 18:54:19.385681', '3', true, 0, 3, 314, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (315, '2022-01-02 18:54:19.385681', '1', true, 1, 3, 314, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (316, '2022-01-02 18:54:19.385681', '2', false, 0, 3, 314, 3);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (322, '2022-01-02 19:04:50.571605', 'Film 3', true, 1, 3, 111, NULL);
INSERT INTO public.variant (id, createdat, text, visible, votes, createdby_id, poll_id, removedby_id) VALUES (117, '2022-01-02 17:04:17.326546', 'Nějaký film 3', false, 0, 7, 111, 3);


--
-- Data for Name: variantcomment; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.variantcomment (id, createdat, text, visible, createdby_id, removedby_id, repliedto_id, variant_id) VALUES (320, '2022-01-02 18:57:12.638372', 'ooooooooooo', false, 3, NULL, NULL, 315);


--
-- Data for Name: video; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.video (id, url, post_id) VALUES (106, 'video.com', 102);
INSERT INTO public.video (id, url, post_id) VALUES (318, 'string', 313);


--
-- Data for Name: voters; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.voters (poll_id, voter_id) VALUES (314, 3);
INSERT INTO public.voters (poll_id, voter_id) VALUES (111, 3);


--
-- Name: followed followed_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.followed
    ADD CONSTRAINT followed_pkey PRIMARY KEY (followed_id, follower_id);


--
-- Name: moderatorrequest moderatorrequest_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.moderatorrequest
    ADD CONSTRAINT moderatorrequest_pkey PRIMARY KEY (id);


--
-- Name: photo photo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.photo
    ADD CONSTRAINT photo_pkey PRIMARY KEY (id);


--
-- Name: poll poll_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.poll
    ADD CONSTRAINT poll_pkey PRIMARY KEY (id);


--
-- Name: post post_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_pkey PRIMARY KEY (id);


--
-- Name: postcomment postcomment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.postcomment
    ADD CONSTRAINT postcomment_pkey PRIMARY KEY (id);


--
-- Name: sequence sequence_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sequence
    ADD CONSTRAINT sequence_pkey PRIMARY KEY (seq_name);


--
-- Name: user user_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_email_key UNIQUE (email);


--
-- Name: user user_phonenumber_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_phonenumber_key UNIQUE (phonenumber);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: user user_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_username_key UNIQUE (username);


--
-- Name: variant variant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.variant
    ADD CONSTRAINT variant_pkey PRIMARY KEY (id);


--
-- Name: variantcomment variantcomment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.variantcomment
    ADD CONSTRAINT variantcomment_pkey PRIMARY KEY (id);


--
-- Name: video video_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.video
    ADD CONSTRAINT video_pkey PRIMARY KEY (id);


--
-- Name: voters voters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voters
    ADD CONSTRAINT voters_pkey PRIMARY KEY (poll_id, voter_id);


--
-- Name: user FK_user_REMOVEDBY_ID; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT "FK_user_REMOVEDBY_ID" FOREIGN KEY (removedby_id) REFERENCES public."user"(id);


--
-- Name: followed fk_followed_followed_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.followed
    ADD CONSTRAINT fk_followed_followed_id FOREIGN KEY (followed_id) REFERENCES public."user"(id);


--
-- Name: followed fk_followed_follower_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.followed
    ADD CONSTRAINT fk_followed_follower_id FOREIGN KEY (follower_id) REFERENCES public."user"(id);


--
-- Name: moderatorrequest fk_moderatorrequest_acceptor_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.moderatorrequest
    ADD CONSTRAINT fk_moderatorrequest_acceptor_id FOREIGN KEY (acceptor_id) REFERENCES public."user"(id);


--
-- Name: moderatorrequest fk_moderatorrequest_createdby_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.moderatorrequest
    ADD CONSTRAINT fk_moderatorrequest_createdby_id FOREIGN KEY (createdby_id) REFERENCES public."user"(id);


--
-- Name: photo fk_photo_post_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.photo
    ADD CONSTRAINT fk_photo_post_id FOREIGN KEY (post_id) REFERENCES public.post(id);


--
-- Name: poll fk_poll_post_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.poll
    ADD CONSTRAINT fk_poll_post_id FOREIGN KEY (post_id) REFERENCES public.post(id);


--
-- Name: post fk_post_createdby_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT fk_post_createdby_id FOREIGN KEY (createdby_id) REFERENCES public."user"(id);


--
-- Name: post fk_post_removedby_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT fk_post_removedby_id FOREIGN KEY (removedby_id) REFERENCES public."user"(id);


--
-- Name: postcomment fk_postcomment_createdby_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.postcomment
    ADD CONSTRAINT fk_postcomment_createdby_id FOREIGN KEY (createdby_id) REFERENCES public."user"(id);


--
-- Name: postcomment fk_postcomment_post_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.postcomment
    ADD CONSTRAINT fk_postcomment_post_id FOREIGN KEY (post_id) REFERENCES public.post(id);


--
-- Name: postcomment fk_postcomment_removedby_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.postcomment
    ADD CONSTRAINT fk_postcomment_removedby_id FOREIGN KEY (removedby_id) REFERENCES public."user"(id);


--
-- Name: postcomment fk_postcomment_repliedto_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.postcomment
    ADD CONSTRAINT fk_postcomment_repliedto_id FOREIGN KEY (repliedto_id) REFERENCES public.postcomment(id);


--
-- Name: variant fk_variant_createdby_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.variant
    ADD CONSTRAINT fk_variant_createdby_id FOREIGN KEY (createdby_id) REFERENCES public."user"(id);


--
-- Name: variant fk_variant_poll_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.variant
    ADD CONSTRAINT fk_variant_poll_id FOREIGN KEY (poll_id) REFERENCES public.poll(id);


--
-- Name: variant fk_variant_removedby_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.variant
    ADD CONSTRAINT fk_variant_removedby_id FOREIGN KEY (removedby_id) REFERENCES public."user"(id);


--
-- Name: variantcomment fk_variantcomment_createdby_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.variantcomment
    ADD CONSTRAINT fk_variantcomment_createdby_id FOREIGN KEY (createdby_id) REFERENCES public."user"(id);


--
-- Name: variantcomment fk_variantcomment_removedby_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.variantcomment
    ADD CONSTRAINT fk_variantcomment_removedby_id FOREIGN KEY (removedby_id) REFERENCES public."user"(id);


--
-- Name: variantcomment fk_variantcomment_repliedto_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.variantcomment
    ADD CONSTRAINT fk_variantcomment_repliedto_id FOREIGN KEY (repliedto_id) REFERENCES public.variantcomment(id);


--
-- Name: variantcomment fk_variantcomment_variant_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.variantcomment
    ADD CONSTRAINT fk_variantcomment_variant_id FOREIGN KEY (variant_id) REFERENCES public.variant(id);


--
-- Name: video fk_video_post_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.video
    ADD CONSTRAINT fk_video_post_id FOREIGN KEY (post_id) REFERENCES public.post(id);


--
-- Name: voters fk_voters_poll_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voters
    ADD CONSTRAINT fk_voters_poll_id FOREIGN KEY (poll_id) REFERENCES public.poll(id);


--
-- Name: voters fk_voters_voter_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.voters
    ADD CONSTRAINT fk_voters_voter_id FOREIGN KEY (voter_id) REFERENCES public."user"(id);


--
-- PostgreSQL database dump complete
--

