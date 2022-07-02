--
-- PostgreSQL database dump
--

-- Dumped from database version 12.11
-- Dumped by pg_dump version 12.11

-- Started on 2022-06-06 23:15:00

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

DROP DATABASE forum_db;
--
-- TOC entry 2910 (class 1262 OID 16393)
-- Name: forum_db; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE forum_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE forum_db OWNER TO postgres;

\connect forum_db

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
-- TOC entry 6 (class 2615 OID 16394)
-- Name: account; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA account;


ALTER SCHEMA account OWNER TO postgres;

--
-- TOC entry 9 (class 2615 OID 16395)
-- Name: forum; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA forum;


ALTER SCHEMA forum OWNER TO postgres;

--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2911 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 207 (class 1259 OID 16412)
-- Name: information; Type: TABLE; Schema: account; Owner: postgres
--

CREATE TABLE account.information (
    id integer NOT NULL,
    gender integer DEFAULT 0 NOT NULL,
    dob date NOT NULL,
    address character varying,
    first_name character varying NOT NULL,
    last_name character varying NOT NULL,
    email character varying NOT NULL,
    phone character varying NOT NULL,
    created date DEFAULT now() NOT NULL,
    modified date
);


ALTER TABLE account.information OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16410)
-- Name: information_id_seq; Type: SEQUENCE; Schema: account; Owner: postgres
--

CREATE SEQUENCE account.information_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE account.information_id_seq OWNER TO postgres;

--
-- TOC entry 2912 (class 0 OID 0)
-- Dependencies: 206
-- Name: information_id_seq; Type: SEQUENCE OWNED BY; Schema: account; Owner: postgres
--

ALTER SEQUENCE account.information_id_seq OWNED BY account.information.id;


--
-- TOC entry 205 (class 1259 OID 16398)
-- Name: user; Type: TABLE; Schema: account; Owner: postgres
--

CREATE TABLE account."user" (
    id integer NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL,
    role character varying DEFAULT 'ROLE_USER'::character varying NOT NULL,
    enabled integer DEFAULT 0 NOT NULL,
    created date DEFAULT now() NOT NULL,
    modified date,
    deleted integer DEFAULT 0 NOT NULL
);


ALTER TABLE account."user" OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16396)
-- Name: user_id_seq; Type: SEQUENCE; Schema: account; Owner: postgres
--

CREATE SEQUENCE account.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE account.user_id_seq OWNER TO postgres;

--
-- TOC entry 2913 (class 0 OID 0)
-- Dependencies: 204
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: account; Owner: postgres
--

ALTER SEQUENCE account.user_id_seq OWNED BY account."user".id;


--
-- TOC entry 215 (class 1259 OID 16457)
-- Name: clikes; Type: TABLE; Schema: forum; Owner: postgres
--

CREATE TABLE forum.clikes (
    id integer NOT NULL,
    owner_id integer NOT NULL,
    comment_id integer NOT NULL,
    created date DEFAULT now() NOT NULL
);


ALTER TABLE forum.clikes OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16455)
-- Name: clikes_id_seq; Type: SEQUENCE; Schema: forum; Owner: postgres
--

CREATE SEQUENCE forum.clikes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE forum.clikes_id_seq OWNER TO postgres;

--
-- TOC entry 2914 (class 0 OID 0)
-- Dependencies: 214
-- Name: clikes_id_seq; Type: SEQUENCE OWNED BY; Schema: forum; Owner: postgres
--

ALTER SEQUENCE forum.clikes_id_seq OWNED BY forum.clikes.id;


--
-- TOC entry 211 (class 1259 OID 16436)
-- Name: comment; Type: TABLE; Schema: forum; Owner: postgres
--

CREATE TABLE forum.comment (
    id integer NOT NULL,
    owner_id integer NOT NULL,
    post_id integer NOT NULL,
    content character varying NOT NULL,
    created date DEFAULT now() NOT NULL,
    modified date,
    deleted integer DEFAULT 0 NOT NULL
);


ALTER TABLE forum.comment OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16434)
-- Name: comment_id_seq; Type: SEQUENCE; Schema: forum; Owner: postgres
--

CREATE SEQUENCE forum.comment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE forum.comment_id_seq OWNER TO postgres;

--
-- TOC entry 2915 (class 0 OID 0)
-- Dependencies: 210
-- Name: comment_id_seq; Type: SEQUENCE OWNED BY; Schema: forum; Owner: postgres
--

ALTER SEQUENCE forum.comment_id_seq OWNED BY forum.comment.id;


--
-- TOC entry 213 (class 1259 OID 16448)
-- Name: plikes; Type: TABLE; Schema: forum; Owner: postgres
--

CREATE TABLE forum.plikes (
    id integer NOT NULL,
    owner_id integer NOT NULL,
    post_id integer NOT NULL,
    created date DEFAULT now() NOT NULL
);


ALTER TABLE forum.plikes OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16446)
-- Name: plikes_id_seq; Type: SEQUENCE; Schema: forum; Owner: postgres
--

CREATE SEQUENCE forum.plikes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE forum.plikes_id_seq OWNER TO postgres;

--
-- TOC entry 2916 (class 0 OID 0)
-- Dependencies: 212
-- Name: plikes_id_seq; Type: SEQUENCE OWNED BY; Schema: forum; Owner: postgres
--

ALTER SEQUENCE forum.plikes_id_seq OWNED BY forum.plikes.id;


--
-- TOC entry 217 (class 1259 OID 16466)
-- Name: post; Type: TABLE; Schema: forum; Owner: postgres
--

CREATE TABLE forum.post (
    id integer NOT NULL,
    author_id integer NOT NULL,
    title character varying NOT NULL,
    content character varying NOT NULL,
    view integer DEFAULT 0 NOT NULL,
    category_id integer NOT NULL,
    created date DEFAULT now() NOT NULL,
    modified date,
    enabled integer DEFAULT 0 NOT NULL,
    deleted integer DEFAULT 0 NOT NULL
);


ALTER TABLE forum.post OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16464)
-- Name: post_id_seq; Type: SEQUENCE; Schema: forum; Owner: postgres
--

CREATE SEQUENCE forum.post_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE forum.post_id_seq OWNER TO postgres;

--
-- TOC entry 2917 (class 0 OID 0)
-- Dependencies: 216
-- Name: post_id_seq; Type: SEQUENCE OWNED BY; Schema: forum; Owner: postgres
--

ALTER SEQUENCE forum.post_id_seq OWNED BY forum.post.id;


--
-- TOC entry 209 (class 1259 OID 16425)
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    id integer NOT NULL,
    category_name character varying NOT NULL,
    path character varying NOT NULL
);


ALTER TABLE public.category OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 16423)
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.category_id_seq OWNER TO postgres;

--
-- TOC entry 2918 (class 0 OID 0)
-- Dependencies: 208
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.category_id_seq OWNED BY public.category.id;


--
-- TOC entry 2735 (class 2604 OID 16415)
-- Name: information id; Type: DEFAULT; Schema: account; Owner: postgres
--

ALTER TABLE ONLY account.information ALTER COLUMN id SET DEFAULT nextval('account.information_id_seq'::regclass);


--
-- TOC entry 2730 (class 2604 OID 16401)
-- Name: user id; Type: DEFAULT; Schema: account; Owner: postgres
--

ALTER TABLE ONLY account."user" ALTER COLUMN id SET DEFAULT nextval('account.user_id_seq'::regclass);


--
-- TOC entry 2744 (class 2604 OID 16460)
-- Name: clikes id; Type: DEFAULT; Schema: forum; Owner: postgres
--

ALTER TABLE ONLY forum.clikes ALTER COLUMN id SET DEFAULT nextval('forum.clikes_id_seq'::regclass);


--
-- TOC entry 2739 (class 2604 OID 16439)
-- Name: comment id; Type: DEFAULT; Schema: forum; Owner: postgres
--

ALTER TABLE ONLY forum.comment ALTER COLUMN id SET DEFAULT nextval('forum.comment_id_seq'::regclass);


--
-- TOC entry 2742 (class 2604 OID 16451)
-- Name: plikes id; Type: DEFAULT; Schema: forum; Owner: postgres
--

ALTER TABLE ONLY forum.plikes ALTER COLUMN id SET DEFAULT nextval('forum.plikes_id_seq'::regclass);


--
-- TOC entry 2746 (class 2604 OID 16469)
-- Name: post id; Type: DEFAULT; Schema: forum; Owner: postgres
--

ALTER TABLE ONLY forum.post ALTER COLUMN id SET DEFAULT nextval('forum.post_id_seq'::regclass);


--
-- TOC entry 2738 (class 2604 OID 16428)
-- Name: category id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category ALTER COLUMN id SET DEFAULT nextval('public.category_id_seq'::regclass);


--
-- TOC entry 2894 (class 0 OID 16412)
-- Dependencies: 207
-- Data for Name: information; Type: TABLE DATA; Schema: account; Owner: postgres
--

INSERT INTO account.information VALUES (1, 0, '2001-11-11', 'abc', 'test', 'account', 'newstarts2001@gmail.com', '0869728188', '2022-05-30', NULL);
INSERT INTO account.information VALUES (2, 0, '1999-11-11', 'dev', 'admin', 'account', 'dev.tranviethailinh@gmail.com', '0869728188', '2022-05-30', NULL);
INSERT INTO account.information VALUES (3, 0, '1998-01-01', 'dev', 'dev', 'account', 'tranviethailinhw@gmail.com', '0869728188', '2022-05-30', NULL);
INSERT INTO account.information VALUES (4, 1, '2000-12-12', 'afsfd', 'Lucy', 'Guo', 'abc@gmail.com', '0987654321', '2022-05-30', NULL);
INSERT INTO account.information VALUES (5, 0, '1999-07-12', 'bbbb', 'Eric', 'Chen', 'mkamfk@gg.com', '0123456789', '2022-05-30', NULL);
INSERT INTO account.information VALUES (6, 0, '2000-07-07', 'abc st', 'Alonso', 'Davies', 'abc@gg.com', '0987654322', '2022-06-01', NULL);
INSERT INTO account.information VALUES (7, 0, '1993-01-13', '30 Hoang Hoa Tham st, Ba Dinh, Hanoi', 'Melinda', 'Edgard', 'acc@text.com', '0983634001', '2022-06-02', NULL);


--
-- TOC entry 2892 (class 0 OID 16398)
-- Dependencies: 205
-- Data for Name: user; Type: TABLE DATA; Schema: account; Owner: postgres
--

INSERT INTO account."user" VALUES (2, 'test', '$2a$12$JJbzl35jLhcKdjrXUajVjOilJg9JudMBr59YzYH0aUu1AOt0kWUl2', 'ROLE_DEV', 0, '2022-05-30', NULL, 0);
INSERT INTO account."user" VALUES (3, 'dev', '$2a$12$a5nJHdcdNWkclZQqSRIiJOgZzojWBi8c4ngWbjkyQSUoRQejto/Sa', 'ROLE_DEV', 0, '2022-05-30', NULL, 0);
INSERT INTO account."user" VALUES (4, 'user1', '$2a$12$h6UWGsANqqActA9/aiz8VO4BB3T/m629/CFvbFQqQYgqpSbMMJfgW', 'ROLE_USER', 0, '2022-05-30', NULL, 0);
INSERT INTO account."user" VALUES (5, 'user2', '$2a$12$XCja0PTgMApVxVE9t1.RTOzJu1B8s9rMH3VYldKJ.29v4m3m1CLuu', 'ROLE_USER', 0, '2022-05-30', NULL, 0);
INSERT INTO account."user" VALUES (1, 'admin', '$2a$12$IQ6f8TL3F/zW/4l.Uhpof.X30qWrk.PR//bGqPZE9j3uWo49PzGiu', 'ROLE_ADMIN', 0, '2022-05-30', '2022-05-30', 0);
INSERT INTO account."user" VALUES (6, 'user3', '$2a$10$CRZr0gBI9Bw2HchF.S7kZ.1hVQi4hkHF9Ooz181Uc0BjPnetejrw6', 'ROLE_USER', 0, '2022-06-01', '2022-06-01', 0);
INSERT INTO account."user" VALUES (7, 'user4', '$2a$10$RugMfADKRYyNSZ/pD/ZY5eUdIU0cA2J5PM.uOqg.8X52xzMzvE/IC', 'ROLE_USER', 0, '2022-06-02', '2022-06-02', 0);


--
-- TOC entry 2902 (class 0 OID 16457)
-- Dependencies: 215
-- Data for Name: clikes; Type: TABLE DATA; Schema: forum; Owner: postgres
--

INSERT INTO forum.clikes VALUES (1, 1, 1, '2022-05-30');
INSERT INTO forum.clikes VALUES (2, 4, 1, '2022-05-30');
INSERT INTO forum.clikes VALUES (3, 5, 1, '2022-05-30');
INSERT INTO forum.clikes VALUES (4, 1, 2, '2022-05-31');


--
-- TOC entry 2898 (class 0 OID 16436)
-- Dependencies: 211
-- Data for Name: comment; Type: TABLE DATA; Schema: forum; Owner: postgres
--

INSERT INTO forum.comment VALUES (2, 4, 1, '2nd cmt', '2022-05-30', NULL, 0);
INSERT INTO forum.comment VALUES (1, 1, 1, '1st cmt', '2022-05-30', '2022-05-30', 0);
INSERT INTO forum.comment VALUES (3, 1, 2, 'abc xyz', '2022-05-30', NULL, 0);
INSERT INTO forum.comment VALUES (4, 1, 3, 'test cmt add', '2022-05-30', NULL, 0);


--
-- TOC entry 2900 (class 0 OID 16448)
-- Dependencies: 213
-- Data for Name: plikes; Type: TABLE DATA; Schema: forum; Owner: postgres
--

INSERT INTO forum.plikes VALUES (1, 1, 1, '2022-05-30');
INSERT INTO forum.plikes VALUES (2, 1, 2, '2022-05-30');
INSERT INTO forum.plikes VALUES (3, 4, 1, '2022-05-30');
INSERT INTO forum.plikes VALUES (4, 5, 1, '2022-05-30');


--
-- TOC entry 2904 (class 0 OID 16466)
-- Dependencies: 217
-- Data for Name: post; Type: TABLE DATA; Schema: forum; Owner: postgres
--

INSERT INTO forum.post VALUES (1, 4, 'First post title', '<div><p style="text-indent: 20px" style="text-indent: 20px">Sir Richard Branson&rsquo;s airline, in common with most carriers, has until now banned visible tattoos, only hiring staff who could conceal any ink work under their uniform.</p> <p style="text-indent: 20px">Estelle Hollingsworth, Virgin Atlantic&rsquo;s chief people officer, said restrictions were being relaxed &ldquo;in line with our focus on inclusion and championing individuality&rdquo;.</p> <p style="text-indent: 20px">She said: &ldquo;At Virgin Atlantic, we want everyone to be themselves and know that they belong. Many people use tattoos to express their unique identities and our customer-facing and uniformed colleagues should not be excluded from doing so if they choose.&rdquo;</p> <p style="text-indent: 20px">Facial and neck tattoos will remain banned for flight attendants &ndash; for now, although the airline is considering relaxing the rules at a later date. Tattoos with swearing, or deemed culturally inappropriate, or those that refer to nudity, violence, drugs or alcohol are off limits. Prison-style love/hate knuckle tattoos will also remain proscribed.</p> <p style="text-indent: 20px">Virgin Atlantic said crew who would benefit included those with full-arm tattoos who previously had to wear long-sleeved shirts instead of the standard short-sleeved version while on duty. Others have concealed smaller tattoos with makeup. Aeroplane tattoos are popular among crew, the airline added.</p> <p style="text-indent: 20px">Josie Hopkins has just completed her training as cabin crew and will be allowed to have her tattoos on show when she makes her first flight next month. &ldquo;Having worked for another airline previously and other jobs where my tattoos have to be covered, it felt like I wasn&rsquo;t allowed to be myself,&rdquo; she said.</p> <p style="text-indent: 20px">Terry Nunn said his tattoos were of London landmarks. &ldquo;When we have customers onboard and are visiting London for the first time, I like to share with them any tips/secrets on the best places to eat/see in the capital. Now I can show them my tattoos too.</p> <p style="text-indent: 20px">&ldquo;I&rsquo;m so pleased we have changed the policy to allow us cabin crew to express our individuality.&rdquo;</p> <p style="text-indent: 20px">Virgin Atlantic was one of the first airlines to relax strict makeup rules for cabin crew. Female crew were forced to wear makeup on duty until 2019, when it scrapped the rule and allowed them to wear trousers instead of a skirt if they chose. The move was viewed as a significant change in an industry where female crew, particularly on the full-service international airlines, are still often trained in applying makeup to airline regulations.</p> <p style="text-indent: 20px">The embracing of body art reflects Virgin&rsquo;s latest branding campaign, with adverts showing diverse passengers and crew to the soundtrack of &ldquo;I am what I am&rdquo; &ndash; including one with multiple tongue piercings, triggering the airport security. However, Virgin said such piercings would still not be allowed for crew.</p></div>', 43, 2, '2022-05-30', '2022-06-04', 0, 0);
INSERT INTO forum.post VALUES (3, 1, 'test add p', '<p>Th&ocirc;ng thường, bạn hay sử dụng 2 loại context l&agrave; Application v&agrave; Activity:</p> <ul> <li>Application context: gắn liền với v&ograve;ng đời của ứng dụng v&agrave; lu&ocirc;n lu&ocirc;n giống nhau xuy&ecirc;n suốt v&ograve;ng đời ứng dụng. getApplication(), getApplicationContext().</li> <li>Activity context: gắn liền với v&ograve;ng đời của Activity v&agrave; n&oacute; sẽ bị hủy khi activity bị hủy. getBaseContext(), Activity.this.</li> </ul> <p>Tips: Bất cứ khi n&agrave;o bạn cần thao t&aacute;c với Views h&atilde;y sử dụng Activity context, c&ograve;n kh&ocirc;ng sử dụng Application context l&agrave; đủ. V&iacute; dụ: khi bạn sử dụng Toast, bạn c&oacute; thể sử dụng 1 trong 2 loại contex tr&ecirc;n, nhưng v&igrave; n&oacute; c&oacute; thể được show l&ecirc;n từ bất cứ nơi đ&acirc;u trong ứng dụng, v&igrave; vậy bạn n&ecirc;n sử dụng Application context l&agrave; hợp l&yacute; (Trong head-first android họ cũng sử dụng getApplicationContext());</p> <p>Tr&aacute;nh sử dụng getBaseContext() - lớp n&agrave;y được triển khai khi 1 class extends từ ContextWrapper. M&agrave; lớp n&agrave;y lại c&oacute; khoảng 40 lớp con trực tiếp v&agrave; kh&ocirc;ng trực tiếp. V&igrave; vậy, n&ecirc;n gọi trực tiếp đến getContext, Activity, Fragment... để tr&aacute;nh g&acirc;y ra memory leak.</p>', 6, 2, '2022-05-30', '2022-06-01', 0, 0);
INSERT INTO forum.post VALUES (2, 5, 'dev', '<p>Với số lượng nước đi lớn như vậy, c&aacute;c thuật to&aacute;n trong c&aacute;c chương tr&igrave;nh m&aacute;y t&iacute;nh chơi cờ hiện nay mới chỉ x&eacute;t được một số lượng rất nhỏ kh&ocirc;ng gian c&aacute;c bước đi. Do đ&oacute; c&aacute;c phần mềm n&agrave;y chưa thắng được c&aacute;c đại kiện tướng, những cờ thủ chuy&ecirc;n nghiệp, cho đến khi AlphaGo ra đời. Theo m&ocirc; tả tr&ecirc;n<span>&nbsp;</span><a href="https://www.deepmind.com/research/highlighted-research/alphago" target="_blank" rel="noopener">trang chủ</a>, đ&acirc;y l&agrave; phần mềm m&aacute;y t&iacute;nh đầu ti&ecirc;n đ&aacute;nh bại một người chơi cờ v&acirc;y chuy&ecirc;n nghiệp. Điều đặc biệt l&agrave;m cho AlphaGo trở n&ecirc;n mạnh mẽ như vậy một phần l&agrave; do sự ph&aacute;t triển của tr&iacute; tuệ nh&acirc;n tạo, v&agrave; một nh&aacute;nh trong đ&oacute; l&agrave;<span>&nbsp;</span><strong>Học tăng cường (Reinforcement learning)</strong>. AlphaGo đ&atilde; tự chơi với ch&iacute;nh n&oacute; v&agrave; cải thiện dần dần c&aacute;c<span>&nbsp;</span><strong>mạng neural</strong><span>&nbsp;</span>được sử dụng để đ&aacute;nh gi&aacute; b&agrave;n cờ, đưa ra chiến lược v&agrave; quyết định c&aacute;c nước đi.</p> <p>Trong b&agrave;i viết n&agrave;y m&igrave;nh sẽ tr&igrave;nh b&agrave;y một số<span>&nbsp;</span><strong>kh&aacute;i niệm cơ bản</strong><span>&nbsp;</span>của<span>&nbsp;</span><strong>học tăng cường</strong>. B&agrave;i viết n&agrave;y nằm trong series m&agrave; m&igrave;nh đ&atilde; l&ecirc;n kế hoạch viết trong qu&aacute; tr&igrave;nh t&igrave;m hiểu về học tăng cường, nhằm mục đ&iacute;ch tổng hợp lại những điều m&igrave;nh học v&agrave; chia sẻ kiến thức. Do đ&oacute;, m&igrave;nh sẽ kh&ocirc;ng thể tr&aacute;nh khỏi những thiếu s&oacute;t, rất mong nhận được sự g&oacute;p &yacute; của c&aacute;c bạn.</p>', 12, 1, '2022-05-30', '2022-06-03', 0, 0);


--
-- TOC entry 2896 (class 0 OID 16425)
-- Dependencies: 209
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.category VALUES (1, 'City life', 'city-life');
INSERT INTO public.category VALUES (2, 'Yolo', 'yolo');
INSERT INTO public.category VALUES (3, 'Dat ass', 'dat-ass');


--
-- TOC entry 2919 (class 0 OID 0)
-- Dependencies: 206
-- Name: information_id_seq; Type: SEQUENCE SET; Schema: account; Owner: postgres
--

SELECT pg_catalog.setval('account.information_id_seq', 7, true);


--
-- TOC entry 2920 (class 0 OID 0)
-- Dependencies: 204
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: account; Owner: postgres
--

SELECT pg_catalog.setval('account.user_id_seq', 7, true);


--
-- TOC entry 2921 (class 0 OID 0)
-- Dependencies: 214
-- Name: clikes_id_seq; Type: SEQUENCE SET; Schema: forum; Owner: postgres
--

SELECT pg_catalog.setval('forum.clikes_id_seq', 4, true);


--
-- TOC entry 2922 (class 0 OID 0)
-- Dependencies: 210
-- Name: comment_id_seq; Type: SEQUENCE SET; Schema: forum; Owner: postgres
--

SELECT pg_catalog.setval('forum.comment_id_seq', 5, false);


--
-- TOC entry 2923 (class 0 OID 0)
-- Dependencies: 212
-- Name: plikes_id_seq; Type: SEQUENCE SET; Schema: forum; Owner: postgres
--

SELECT pg_catalog.setval('forum.plikes_id_seq', 5, true);


--
-- TOC entry 2924 (class 0 OID 0)
-- Dependencies: 216
-- Name: post_id_seq; Type: SEQUENCE SET; Schema: forum; Owner: postgres
--

SELECT pg_catalog.setval('forum.post_id_seq', 3, true);


--
-- TOC entry 2925 (class 0 OID 0)
-- Dependencies: 208
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_id_seq', 3, true);


--
-- TOC entry 2754 (class 2606 OID 16422)
-- Name: information information_pk; Type: CONSTRAINT; Schema: account; Owner: postgres
--

ALTER TABLE ONLY account.information
    ADD CONSTRAINT information_pk PRIMARY KEY (id);


--
-- TOC entry 2752 (class 2606 OID 16409)
-- Name: user user_pk; Type: CONSTRAINT; Schema: account; Owner: postgres
--

ALTER TABLE ONLY account."user"
    ADD CONSTRAINT user_pk PRIMARY KEY (id);


--
-- TOC entry 2762 (class 2606 OID 16463)
-- Name: clikes clikes_pk; Type: CONSTRAINT; Schema: forum; Owner: postgres
--

ALTER TABLE ONLY forum.clikes
    ADD CONSTRAINT clikes_pk PRIMARY KEY (id);


--
-- TOC entry 2758 (class 2606 OID 16445)
-- Name: comment comment_pk; Type: CONSTRAINT; Schema: forum; Owner: postgres
--

ALTER TABLE ONLY forum.comment
    ADD CONSTRAINT comment_pk PRIMARY KEY (id);


--
-- TOC entry 2760 (class 2606 OID 16454)
-- Name: plikes plikes_pk; Type: CONSTRAINT; Schema: forum; Owner: postgres
--

ALTER TABLE ONLY forum.plikes
    ADD CONSTRAINT plikes_pk PRIMARY KEY (id);


--
-- TOC entry 2764 (class 2606 OID 16478)
-- Name: post post_pk; Type: CONSTRAINT; Schema: forum; Owner: postgres
--

ALTER TABLE ONLY forum.post
    ADD CONSTRAINT post_pk PRIMARY KEY (id);


--
-- TOC entry 2756 (class 2606 OID 16433)
-- Name: category category_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pk PRIMARY KEY (id);


-- Completed on 2022-06-06 23:15:00

--
-- PostgreSQL database dump complete
--

