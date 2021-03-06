PGDMP     '    #                x        	   eLearning    10.4    12.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    25541 	   eLearning    DATABASE     �   CREATE DATABASE "eLearning" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';
    DROP DATABASE "eLearning";
                postgres    false            �            1259    25642    students    TABLE        CREATE TABLE public.students (
    student_id text NOT NULL,
    teacher_id text,
    theory_id text NOT NULL,
    password text NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    salt bytea NOT NULL,
    progress double precision
);
    DROP TABLE public.students;
       public            postgres    false            �            1259    25687    students_tests    TABLE     �   CREATE TABLE public.students_tests (
    student_id text NOT NULL,
    test_id text NOT NULL,
    st_results text NOT NULL,
    weakness text
);
 "   DROP TABLE public.students_tests;
       public            postgres    false            �            1259    25613    teachers    TABLE     �   CREATE TABLE public.teachers (
    teacher_id text NOT NULL,
    password text NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    salt bytea NOT NULL
);
    DROP TABLE public.teachers;
       public            postgres    false            �            1259    25661    tests    TABLE     V   CREATE TABLE public.tests (
    test_id text NOT NULL,
    test_data text NOT NULL
);
    DROP TABLE public.tests;
       public            postgres    false            �            1259    25669    tests_theory    TABLE     ]   CREATE TABLE public.tests_theory (
    theory_id text NOT NULL,
    test_id text NOT NULL
);
     DROP TABLE public.tests_theory;
       public            postgres    false            �            1259    25621    theory    TABLE     \   CREATE TABLE public.theory (
    theory_id text NOT NULL,
    section_data text NOT NULL
);
    DROP TABLE public.theory;
       public            postgres    false                      0    25642    students 
   TABLE DATA           v   COPY public.students (student_id, teacher_id, theory_id, password, first_name, last_name, salt, progress) FROM stdin;
    public          postgres    false    198   �                  0    25687    students_tests 
   TABLE DATA           S   COPY public.students_tests (student_id, test_id, st_results, weakness) FROM stdin;
    public          postgres    false    201   S!                 0    25613    teachers 
   TABLE DATA           U   COPY public.teachers (teacher_id, password, first_name, last_name, salt) FROM stdin;
    public          postgres    false    196   p!                 0    25661    tests 
   TABLE DATA           3   COPY public.tests (test_id, test_data) FROM stdin;
    public          postgres    false    199   �!                 0    25669    tests_theory 
   TABLE DATA           :   COPY public.tests_theory (theory_id, test_id) FROM stdin;
    public          postgres    false    200   "                 0    25621    theory 
   TABLE DATA           9   COPY public.theory (theory_id, section_data) FROM stdin;
    public          postgres    false    197   �"       �
           2606    25649    students students_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_pkey PRIMARY KEY (student_id);
 @   ALTER TABLE ONLY public.students DROP CONSTRAINT students_pkey;
       public            postgres    false    198            �
           2606    25694 "   students_tests students_tests_pkey 
   CONSTRAINT     q   ALTER TABLE ONLY public.students_tests
    ADD CONSTRAINT students_tests_pkey PRIMARY KEY (student_id, test_id);
 L   ALTER TABLE ONLY public.students_tests DROP CONSTRAINT students_tests_pkey;
       public            postgres    false    201    201            �
           2606    25620    teachers teachers_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_pkey PRIMARY KEY (teacher_id);
 @   ALTER TABLE ONLY public.teachers DROP CONSTRAINT teachers_pkey;
       public            postgres    false    196            �
           2606    25668    tests tests_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.tests
    ADD CONSTRAINT tests_pkey PRIMARY KEY (test_id);
 :   ALTER TABLE ONLY public.tests DROP CONSTRAINT tests_pkey;
       public            postgres    false    199            �
           2606    25676    tests_theory tests_theory_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public.tests_theory
    ADD CONSTRAINT tests_theory_pkey PRIMARY KEY (theory_id, test_id);
 H   ALTER TABLE ONLY public.tests_theory DROP CONSTRAINT tests_theory_pkey;
       public            postgres    false    200    200            �
           2606    25628    theory theory_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.theory
    ADD CONSTRAINT theory_pkey PRIMARY KEY (theory_id);
 <   ALTER TABLE ONLY public.theory DROP CONSTRAINT theory_pkey;
       public            postgres    false    197            �
           2606    25650 !   students students_teacher_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public.teachers(teacher_id);
 K   ALTER TABLE ONLY public.students DROP CONSTRAINT students_teacher_id_fkey;
       public          postgres    false    198    196    2695            �
           2606    25695 -   students_tests students_tests_student_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.students_tests
    ADD CONSTRAINT students_tests_student_id_fkey FOREIGN KEY (student_id) REFERENCES public.students(student_id);
 W   ALTER TABLE ONLY public.students_tests DROP CONSTRAINT students_tests_student_id_fkey;
       public          postgres    false    201    2699    198            �
           2606    25700 *   students_tests students_tests_test_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.students_tests
    ADD CONSTRAINT students_tests_test_id_fkey FOREIGN KEY (test_id) REFERENCES public.tests(test_id);
 T   ALTER TABLE ONLY public.students_tests DROP CONSTRAINT students_tests_test_id_fkey;
       public          postgres    false    201    2701    199            �
           2606    25655     students students_theory_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_theory_id_fkey FOREIGN KEY (theory_id) REFERENCES public.theory(theory_id);
 J   ALTER TABLE ONLY public.students DROP CONSTRAINT students_theory_id_fkey;
       public          postgres    false    198    197    2697            �
           2606    25682 &   tests_theory tests_theory_test_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.tests_theory
    ADD CONSTRAINT tests_theory_test_id_fkey FOREIGN KEY (test_id) REFERENCES public.tests(test_id);
 P   ALTER TABLE ONLY public.tests_theory DROP CONSTRAINT tests_theory_test_id_fkey;
       public          postgres    false    2701    199    200            �
           2606    25677 (   tests_theory tests_theory_theory_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.tests_theory
    ADD CONSTRAINT tests_theory_theory_id_fkey FOREIGN KEY (theory_id) REFERENCES public.theory(theory_id);
 R   ALTER TABLE ONLY public.tests_theory DROP CONSTRAINT tests_theory_theory_id_fkey;
       public          postgres    false    197    2697    200               l   x��;�  ��&�|�D��,�6C���P��}��~`� �8c��˱w]f��)M��zajёg�Yɭ�$G&#�
����\��{ۆ1�Hk6��Zr��8B}!�            x������ � �            x������ � �         �   x�m��r�0 @�u�1 ����q���F�X�#���/��YܹP�h�Q|jZ3̽���Z;��k�é��E����P�n����2��3@S�bB����,�m=
� R���Ey��ܽ� V�y	<�D���B>�@K��A�=H�(�7����
6L)�K�e"�,��F�]+��y�r�xnC���ܷ^�t��!'@�����<��KE��!���� �Z��            x������ � �         �  x�U���� �3��d�$���n%�)�!���q|xF���:����u^������x�w��tG��wԌrG���X��z�b�3T3�Pnx�z�m4��[[3R�k`9��Vj`[F͝ՠ����8��s �z7~H��)ᔌ��$��M��	)A5��s+A=�2��2ԛ\y�\x�rp9#ek8�#������
��K[3�CK0��G�Ew��V3xtu�2�O��PN�[^�z������Wo+m�����-/|8����+�[x��{�Ov����m�׊�^�e$�N�꧅wM��W��B�,��W��{;ԛ^{�������3<:}ykf����<��
���<���^e�8��#v�;��ص�1\ 8���p�sA��s�����a,��v2�>6��=�Q�nA	nۘn��c��fn0�#��:�@�v�0�qA�
������u����t     