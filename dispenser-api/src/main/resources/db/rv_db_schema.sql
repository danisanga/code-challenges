PGDMP      ,                |            postgres_rv_database    13.4    16.0     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16384    postgres_rv_database    DATABASE        CREATE DATABASE postgres_rv_database WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';
 $   DROP DATABASE postgres_rv_database;
                rv_user    false                        2615    16385 	   my_schema    SCHEMA        CREATE SCHEMA my_schema;
    DROP SCHEMA my_schema;
                rv_user    false                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                rv_user    false            �           0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   rv_user    false    4            �            1259    16386 
   dispensers    TABLE     ^   CREATE TABLE my_schema.dispensers (
    id uuid NOT NULL,
    flow_volume numeric NOT NULL
);
 !   DROP TABLE my_schema.dispensers;
    	   my_schema         heap    rv_user    false    6            �            1259    16394    uses    TABLE     �   CREATE TABLE my_schema.uses (
    id bigint NOT NULL,
    opened_at timestamp without time zone NOT NULL,
    dispenser_id uuid NOT NULL,
    closed_at timestamp without time zone,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);
    DROP TABLE my_schema.uses;
    	   my_schema         heap    rv_user    false    6            �            1259    16419    uses_id_seq    SEQUENCE     �   ALTER TABLE my_schema.uses ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME my_schema.uses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
         	   my_schema          rv_user    false    6    202            �          0    16386 
   dispensers 
   TABLE DATA           8   COPY my_schema.dispensers (id, flow_volume) FROM stdin;
 	   my_schema          rv_user    false    201   #       �          0    16394    uses 
   TABLE DATA           U   COPY my_schema.uses (id, opened_at, dispenser_id, closed_at, created_at) FROM stdin;
 	   my_schema          rv_user    false    202   �       �           0    0    uses_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('my_schema.uses_id_seq', 9, true);
       	   my_schema          rv_user    false    203            *           2606    16393    dispensers dispensers_pkey 
   CONSTRAINT     [   ALTER TABLE ONLY my_schema.dispensers
    ADD CONSTRAINT dispensers_pkey PRIMARY KEY (id);
 G   ALTER TABLE ONLY my_schema.dispensers DROP CONSTRAINT dispensers_pkey;
    	   my_schema            rv_user    false    201            ,           2606    16414    uses uses_pkey 
   CONSTRAINT     O   ALTER TABLE ONLY my_schema.uses
    ADD CONSTRAINT uses_pkey PRIMARY KEY (id);
 ;   ALTER TABLE ONLY my_schema.uses DROP CONSTRAINT uses_pkey;
    	   my_schema            rv_user    false    202            -           2606    16402    uses FK_dispenser_id_id    FK CONSTRAINT     �   ALTER TABLE ONLY my_schema.uses
    ADD CONSTRAINT "FK_dispenser_id_id" FOREIGN KEY (dispenser_id) REFERENCES my_schema.dispensers(id);
 F   ALTER TABLE ONLY my_schema.uses DROP CONSTRAINT "FK_dispenser_id_id";
    	   my_schema          rv_user    false    201    2858    202            �   ~   x�5��C1��x���a�46��GH���J
?*���B�)���}��:�k��2[��Sp�6��a���:m��ɋRiZ�-y�2�X�⁰ʻ���SǍn�u���d��	��/ET���i�} m�+O      �   �   x���Kn� ��������t ��ʦ��iUy��̓-� $$ �(ڨ6�\T��DX��M��o���:f�a:_0�����*���=��������(�,"����SjD�C�R-h��#����,��e1�T�_~���7��.x
�n.8r(量���a4�&����p��x6�{'3�x��Ĳ�:��9�,d(�y]
8n_s#ޛF�����(�U�gN)}Ԩ�l     