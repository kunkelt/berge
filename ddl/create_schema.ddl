CREATE TABLE public.galeriebild
(
  id_tour integer NOT NULL,
  sequenz integer NOT NULL,
  dateiname character varying(256) NOT NULL,
  breite integer,
  hoehe integer,
  titel character varying(500) NOT NULL,
  CONSTRAINT pk_bildergalerie PRIMARY KEY (id_tour, sequenz),
  CONSTRAINT galeriebild_id_tour_fkey FOREIGN KEY (id_tour)
      REFERENCES public.tour (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE public.karte
(
  id integer NOT NULL,
  blattnummer character varying(100),
  titel character varying(255) NOT NULL,
  untertitel character varying(255),
  verlag integer,
  massstab character varying(100),
  kartentyp integer NOT NULL,
  ausgabejahr integer,
  extent geometry,
  isbn character varying(100),
  CONSTRAINT pk_karte PRIMARY KEY (id)
);

CREATE TABLE public.kartentyp
(
  id integer NOT NULL,
  typ character varying(100) NOT NULL,
  CONSTRAINT pk_kartentyp PRIMARY KEY (id)
);

CREATE TABLE public.punkt
(
  id integer NOT NULL,
  name character varying(100),
  name2 character varying(100),
  hoehe smallint,
  beschreibung character varying(4000),
  lage geometry NOT NULL,
  typ integer NOT NULL,
  url character varying(500),
  CONSTRAINT pk_punkt PRIMARY KEY (id)
);

CREATE TABLE public.punkttyp
(
  id integer NOT NULL,
  name character varying(100) NOT NULL,
  CONSTRAINT pk_punkttyp PRIMARY KEY (id)
);

CREATE TABLE public.region
(
  id integer NOT NULL,
  name character varying(100) NOT NULL,
  hoechster_punkt integer,
  oberregion integer,
  umgrenzung character varying(4000),
  extent geometry,
  CONSTRAINT pk_region PRIMARY KEY (id),
  CONSTRAINT fk_hoechster FOREIGN KEY (hoechster_punkt)
      REFERENCES public.punkt (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_oberregion FOREIGN KEY (oberregion)
      REFERENCES public.region (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE public.tour
(
  id integer NOT NULL,
  name character varying(500) NOT NULL,
  beschreibung character varying(4000),
  link character varying(200),
  zusatzinfo character varying(4000),
  geplant boolean NOT NULL DEFAULT false,
  CONSTRAINT pk_tour PRIMARY KEY (id)
);

CREATE TABLE public.tour_karte
(
  id_tour integer NOT NULL,
  id_karte integer NOT NULL,
  CONSTRAINT pk_tour_karte PRIMARY KEY (id_tour, id_karte),
  CONSTRAINT fk_karte FOREIGN KEY (id_karte)
      REFERENCES public.karte (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_tour FOREIGN KEY (id_tour)
      REFERENCES public.tour (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE public.tourabschnitt
(
  id_tour integer NOT NULL,
  tag integer NOT NULL,
  sequenz integer NOT NULL,
  von_punkt integer,
  nach_punkt integer,
  CONSTRAINT pk_abschnitt PRIMARY KEY (id_tour, tag, sequenz),
  CONSTRAINT fk_nach_punkt FOREIGN KEY (nach_punkt)
      REFERENCES public.punkt (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_von_punkt FOREIGN KEY (von_punkt)
      REFERENCES public.punkt (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE public.tourentag
(
  id_tour integer NOT NULL,
  tag integer NOT NULL,
  beschreibung character varying(10000),
  datum date NOT NULL,
  region integer NOT NULL,
  hmaufstieg integer,
  hmabstieg integer,
  gehzeit real,
  schwierigkt character varying,
  bilddatei character varying(256),
  bildtitel character varying(500),
  track polygon,
  CONSTRAINT pk_tourentag PRIMARY KEY (id_tour, tag)
);

CREATE TABLE public.verlag
(
  id integer NOT NULL,
  name character varying(100) NOT NULL,
  anschrift character varying(255),
  telefon character varying(100),
  bezugsquelle character varying(255),
  CONSTRAINT pk_verlag PRIMARY KEY (id)
);

CREATE SEQUENCE public.id_karte
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE public.id_kartentyp
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE SEQUENCE public.id_punkt
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE SEQUENCE public.id_punkttyp
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE SEQUENCE public.id_region
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE SEQUENCE public.id_tour
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE SEQUENCE public.id_verlag
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

