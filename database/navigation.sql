-- Optional profiles and navigation only. No user accounts.

COPY schema_utilisateur.tp_menu (menu_id, men_icontype, men_path, men_ytitle, men_type) FROM stdin;
100	assets/icons/users.svg	#	Administration	sub
109		/carrieres/dossier-agents	Dossier agents	link
105		/formations/plan-formation	Plan de formation	link
107		/gpeec/besoin-en-personnel	Besoins en personnel	link
111		/carrieres/mes-demandes	Mes demandes	link
112		/carrieres/demandes-recues	Demandes reÃ§ues	link
101		/utilisateurs/niveau-central	Niveau central	link
102		/utilisateurs/niveau-deconcentre	Niveau dÃ©concentrÃ©	link
126	assets/icons/courrier.svg	/courriers	Courrier DRH	link
139		/gpeec/personnel	Personnel	link
110		/carrieres/mon-dossier	Mon Dossier	link
104		/formations/expression-besoins	Expression de besoins	link
116		/affaires-sociales/demandes-recues	Demandes reÃ§ues	link
115		/affaires-sociales/mes-demandes	Mes demandes	link
117		/gpeec/fiche-etablissement	Fiche Ã©tablissement	link
118		/gpeec/besoin-en-personnel-recues	Besoins en personnel recues	link
119		/gpeec/besoin-en-nombre-gap	Besoin en nombre(GAP)	link
120		/gpeec/horaire-professeur	Horaire Professeurs	link
123		/carrieres/inputation-bulletin	Imputation/Bulletin	link
121		/carrieres/sortie-temporaire	Sortie temporaire	link
122		/carrieres/sortie-definitive	Sortie dÃ©finitive	link
124		/formations/liste-des-formations	Liste des formations	link
125		/formations/mes-formations	Mes formations	link
127		/formations/stages-internes	Stages internes	link
113	assets/icons/dashboard.svg	/dashboard	Tableau de bord	link
103	assets/icons/formations.svg	#	Gestion Formations	sub
108	assets/icons/carrieres.svg	#	Gestion CarriÃ¨res	sub
114	assets/icons/affaires-sociales.svg	#	Affaires sociales	sub
106	assets/icons/gpeec.svg	#	GPEEC	sub
128		/formations/demandes-de-formation	Demandes de formation	link
130	assets/icons/pta.svg	#	Plan Travail Annuel	sub
132		/plan-travail-annuel/parametres	ParamÃ¨tres	link
131		/plan-travail-annuel/pta	PTA	link
134		/gpeec/mes-demande-permutation-recues	Demandes de Permutations reÃ§ues	link
135		/gpeec/demande-mutation-permutation-recues	Traitement Mutation/Permutation	link
133		/gpeec/mes-demandes-mutation-permutation	Demande Mutation/Permutation	link
129	assets/icons/statistique.svg	/statistiques	Suivi indicateurs	link
138	assets/icons/setting.svg	#	ParamÃ©trage	sub
146		/parametrage/specialite	SpecialitÃ©	link
144		/parametrage/ief	IEF	link
143		/parametrage/ia	IA	link
140		/parametrage/recrutement	Recrutement	link
145		/parametrage/etablissement	Etablissement	link
141		/parametrage/directions	Directions	link
147		/parametrage/fonction	Fonctions	link
148		/parametrage/corp-grade	Corp-grade	link
142		/parametrage/bureaux	Bureaux	link
149		/parametrage/divisions	Division	link
150		/parametrage/specialite-etablissement	SpecialitÃ©/Etablissement	link
137		/parametrage/actualites	ActualitÃ©s/ Recrutement	link
151		/utilisateurs/recherche-globale	Recherche Globale	link
152		/parametrage/diplome	Diplome	link
\.

COPY schema_utilisateur.tp_profile (pro_id, pro_code, pro_libelle, pro_type, pro_type_bureau, pro_type_direction, pro_type_division, fonctions) FROM stdin;
136	Agent	Agent	DEC	\N		''	\N
137	Chef-cfp	Directeur CFP	DEC	\N		''	\N
107	Chef-EFF	Directeur EFF	DEC	''		''	\N
104	Assistant-DRH	Assistant DRH	CEN	\N	DRH	''	\N
100	ADMIN-DRH	Admin DRH	CEN	\N	DRH	''	\N
160	Chef-bureau-stai	Chef bureau statistiques informatisÃ©es	CEN	BUSI	\N	''	\N
150	Chef-bureau-bfnic	Chef bureau formation central	CEN	BFNC	\N	''	\N
129	Agent-bureau-dgcaa	Agent Bureau DGCAA	CEN	DGCAA			\N
124	Chef-bureau-dfc	Chef bureau DFC	CEN	DFC			\N
135	Chef-division-dfc	Chef division DFC	CEN	\N		DFC	\N
139	COMAT	Comptable des matiÃ¨res	DEC	\N			\N
120	Professeur	Professeur	DEC	\N		''	\N
117	Representant-IA	ReprÃ©sentant IA	DEC	\N		''	\N
133	Chef-bureau-buse	Chef de bureau Suivi evaluation	CEN	BUSE		\N	\N
146	Chef-bureau-sames	Chef bureau Suivi affaires mÃ©dico-sociales	CEN	BSAMS	\N	''	\N
109	Chef-service	Chef-service	CEN	\N		''	\N
122	Chef-division-dgcaa	Chef division DGCAA	CEN	\N		DGCAA	\N
163	Agent-bureau-more	Agent bureau MobilitÃ© et recrutement	CEN	BUMR	\N	''	\N
153	Agent-bureau-bfnicd	Agent bureau formation dÃ©concentrÃ©	CEN	BUFN	\N	''	\N
152	Chef-bureau-bfnicd	Chef bureau formation dÃ©concentrÃ©	CEN	BUFN	\N	''	\N
142	Chef-division-das	Chef division DAS	CEN	\N		DAS	\N
123	Chef-division-dgpeec	Chef division DGPEEC	CEN	\N		DGPEEC	\N
151	Agent-bureau-bfnic	Agent bureau formation central	CEN	BFNC	\N	''	\N
145	bureau-mo-rec	Bureau mobilitÃ© et recrutement	CEN	BUMR	\N	''	\N
149	Agent-bureau-srs	Agent bureau Suivi des Relations sociales	CEN	BSES	\N	''	\N
105	Chef-bureau	Chef bureau	CEN	\N		''	\N
130	Chef-bureau-asdd	Chef de bureau Assistant Direction	CEN	ASDD		\N	\N
147	Agent-bureau-sames	Agent bureau Suivi affaires mÃ©dico-sociales	CEN	BSAMS	\N	''	\N
110	Chef-travaux	Chef travaux	DEC	''		''	\N
115	Gestionnaire	Gestionnaire	CEN	BUGE	DRH	''	\N
113	Directeur-DRH	Directeur DRH	CEN	''	DRH	''	\N
134	Chef-bureau-buco	Chef de bureau courrier	CEN	BUCO		\N	\N
141	SUR	Surveillant	DEC	\N			\N
154	Chef-bureau-gesca	Chef bureau gestion des carriÃ¨res	CEN	BUGC	\N	''	\N
155	Agent-bureau-gesca	Agent bureau gestion des carriÃ¨res	CEN	BUGC	\N	''	\N
161	Agent-bureau-stai	Agent bureau statistiques informatisÃ©es	CEN	BUSI	\N	''	\N
158	Chef-bureau-meco	Chef bureau mÃ©rites et contentieux	CEN	BUMC	\N	''	\N
159	Agent-bureau-meco	Agent bureau mÃ©rites et contentieux	CEN	BUMC	\N	''	\N
162	Chef-bureau-more	Chef bureau MobilitÃ© et recrutement	CEN	BUMR	\N	''	\N
138	Intendant	Intendant	DEC	\N			\N
157	Agent-bureau-afad	Agent bureau affaires administratives	CEN	BUAA	\N	''	\N
121	Chef-bureau-af	Chef bureau affaire sociale	CEN	BUAS			\N
119	SG-Cabinet	SG Cabinet	CEN	\N	DRH	''	\N
118	ReprÃ©sentant-IEF	ReprÃ©sentant IEF	DEC	\N		''	\N
103	Agent-ministre	Agent ministre	CEN	\N	DRH	''	\N
108	Chef-etablissement	Chef etablissement	DEC	\N		''	\N
148	Chef-bureau-srs	Chef bureau Suivi des Relations sociales	CEN	BSES	\N	''	\N
156	Chef-bureau-afad	Chef bureau affaires administratives	CEN	BUAA	\N	''	\N
111	Coordinateur	Coordinateur	CEN	BUPA	DRH	''	\N
101	Admin-General	Admin General	CEN	\N	DRH	''	\N
106	Chef-division	Chef division	CEN	\N		''	\N
102	Agent-bureau	Agent bureau	CEN	\N		''	\N
116	Representant-BFPA	ReprÃ©sentant BFPA	DEC	\N		''	\N
140	SUG	Surveillant gÃ©nÃ©ral	DEC	\N			\N
112	Directeur-Censeur	Directeur Censeur	DEC	\N		''	\N
126	Chef-bureau-dgpeec	Chef Bureau DGPEEC	CEN	DGPEEC			\N
143	Agent-bureau-das	Agent bureau DAS	CEN	DAS			\N
127	Agent-bureau-dgpeec	Agent Bureau DGPEEC	CEN	DGPEEC			\N
125	Agent-buerau-dfc	Agent Bureau DFC	CEN	DFC			\N
144	Chef-bureau-das	Chef bureau DAS	CEN	DAS			\N
128	Chef-bureau-dgcaa	Chef Bureau DGCAA	CEN	DGCAA			\N
114	Formateur-EFF	Formateur EFF	DEC	\N		''	\N
164	Formateur-CFP	Formateur CFP	DEC	\N	\N	\N	\N
165	Agent-BSEE	Agent Bureau Suivi	CEN	BSDV	DRH	\N	\N
\.

COPY schema_utilisateur.tp_profile_menu_child (pmc_id, smn_id, men_id, pro_id) FROM stdin;
\.

COPY schema_utilisateur.tr_men_sous_menu (men_id, smn_id) FROM stdin;
100	101
100	102
103	104
103	105
106	107
108	109
108	110
108	111
108	112
103	124
103	125
103	127
106	119
106	118
106	117
106	120
108	122
108	121
114	115
114	116
130	131
130	132
106	133
106	135
114	123
106	139
100	138
\.

COPY schema_utilisateur.tr_profilmenu (pro_id, men_id) FROM stdin;
145	113
145	114
145	103
145	108
145	106
134	126
134	114
134	108
102	113
102	103
102	108
102	114
107	113
107	103
107	108
107	114
142	113
142	108
142	114
142	103
117	106
100	137
100	130
113	100
113	106
113	130
113	126
118	106
100	100
100	103
100	108
100	106
100	113
100	129
113	113
113	103
113	108
113	114
100	114
121	108
121	114
121	113
121	103
134	113
134	103
136	113
136	103
136	108
136	114
115	113
115	108
115	114
111	113
111	103
111	108
111	114
109	113
109	103
109	108
109	114
109	106
127	113
127	103
127	108
127	106
127	114
126	113
126	103
126	108
126	106
126	114
123	113
123	103
123	108
123	106
123	114
129	113
129	103
129	108
129	114
128	113
128	103
128	106
128	114
122	113
122	103
122	108
122	114
125	113
125	103
125	108
125	114
124	113
124	103
124	108
124	114
135	113
135	103
135	108
135	114
104	113
104	103
104	114
108	113
108	108
108	114
108	106
108	103
117	113
117	103
117	108
117	114
118	113
118	103
118	108
118	114
116	113
116	103
116	108
116	114
137	113
137	103
137	108
137	114
120	113
120	103
120	108
120	114
110	113
110	103
110	108
110	114
112	113
112	103
112	108
112	114
112	106
111	130
114	113
114	103
114	108
114	106
114	114
120	106
143	113
143	103
143	106
143	108
143	114
135	126
123	126
142	126
122	126
124	126
126	126
144	126
128	126
130	106
150	106
151	106
147	106
146	106
148	106
149	106
156	106
157	106
121	106
134	106
154	106
155	106
115	106
159	106
158	106
162	106
163	106
104	106
105	106
101	106
102	106
106	106
135	106
103	106
119	106
160	106
122	106
142	106
144	106
133	106
111	106
161	106
129	106
124	106
125	106
104	126
136	106
\.

COPY schema_utilisateur.tr_profilemenusousmenu (pmsm_id, men_id, profile_id, smn_id) FROM stdin;
113	113	108	{}
114	103	108	{125}
115	108	108	{110,111,112}
116	114	108	{115,116}
117	106	108	{107,117,120,133,139,135}
118	113	117	{}
119	103	117	{125}
120	108	117	{110,111,112}
121	114	117	{115,123}
122	106	117	{119,1333,135,139}
123	113	118	{}
124	103	118	{125}
125	108	118	{110,111,112}
126	114	118	{115,123}
127	106	118	{119,1333,135,139}
128	103	114	{125}
129	108	114	{110,111}
130	114	114	{115}
131	106	114	{133}
132	103	120	{125}
133	108	120	{110,111}
134	114	120	{115}
135	106	120	{133}
136	113	137	{}
137	103	137	{125}
138	108	137	{110,111,112}
139	114	137	{115,116}
140	106	137	{107,117,120,133,139,135}
141	113	116	{}
142	103	116	{125}
143	108	116	{110,111,112}
144	114	116	{115,123}
145	106	116	{119,1333,135,139}
146	103	110	{125}
147	108	110	{110,111}
148	114	110	{115}
149	106	110	{133}
150	113	112	{}
151	103	112	{125}
152	108	112	{110,111}
153	114	112	{115,116}
154	106	112	{107,117,120,133,139,135}
155	113	107	{}
156	103	107	{125}
157	108	107	{110,111,112}
158	114	107	{115,116}
159	106	107	{107,117,120,133,139,135}
167	130	100	{131}
168	113	113	{}
170	126	113	{}
173	114	113	{115,116,123}
175	130	113	{131}
176	103	104	{125}
177	126	104	{}
178	108	104	{110,111}
179	114	104	{115,123}
180	130	104	{131}
181	113	135	{}
183	126	135	{}
184	108	135	{110,111,112}
185	114	135	{115}
186	106	135	{133,135}
187	130	135	{131}
188	103	124	{125,127}
190	108	124	{110,111}
191	114	124	{115}
192	106	124	{133,135}
193	130	124	{131}
194	103	125	{125,127}
195	108	125	{110,111}
196	114	125	{115}
197	106	125	{133,135}
198	130	125	{131}
199	113	122	{}
200	103	122	{104,105,124,125,127}
202	108	122	{109,110,111,112,121,122}
204	106	122	{133,135}
205	130	122	{131}
206	103	128	{125,127}
160	113	\N	{}
203	114	122	{115}
207	108	128	{109,110,111,121,122}
165	114	100	{115,116,123}
169	103	113	{104,105,124,125,127,128}
172	108	113	{109,110,111,112,121,122,123}
208	114	128	{115,123}
209	106	128	{133,135}
210	130	128	{131}
266	103	129	{125,127}
211	108	129	{110,111}
212	114	129	{115,123}
213	106	129	{133,135}
214	130	129	{131}
215	113	123	{}
216	103	123	{104,128,124,125,127}
217	108	123	{110,111,112}
218	114	123	{115}
219	106	123	{118,119,133,135,139}
220	130	123	{131}
221	103	126	{125,127}
222	108	126	{110,111}
223	114	126	{115}
224	106	126	{118,133,135,139}
225	130	126	{131}
226	103	127	{125,127}
227	108	127	{110,111}
228	114	127	{115}
229	106	127	{118,133,135,139}
230	130	127	{131}
231	113	142	{}
232	103	142	{104,125,127}
233	108	142	{110,111,112}
234	114	142	{115,116,123}
235	106	142	{133,135}
236	103	144	{125,127}
237	108	144	{110,111}
238	114	144	{115,116,123}
239	106	144	{133,135}
240	130	144	{131}
241	103	143	{125,127}
242	108	143	{110,111}
243	114	143	{115,116,123}
245	130	143	{131}
246	103	109	{104,105,125,124,128}
247	108	109	{110,111,112}
248	114	109	{115}
249	106	109	{139}
251	103	111	{125}
252	108	111	{110,111}
253	114	111	{115}
254	130	111	{131,132}
255	103	115	{125}
256	108	115	{110,111}
257	114	115	{115}
258	130	115	{131}
259	103	136	{125}
260	108	136	{110,111}
261	114	136	{115}
262	103	121	{125}
263	108	121	{110,111}
264	114	121	{115,116}
265	130	121	{131}
244	106	143	{133}
161	100	100	{101,102,151}
268	103	164	{125}
269	108	164	{110,111}
270	114	164	{115}
271	106	164	{133}
182	103	135	{104,105,124,125,127}
267	138	100	{137,146,144,143,142,145,141,147,148,149,150,152}
279	106	165	{133}
280	108	165	{110,111}
281	130	165	{131,132}
282	114	165	{115}
287	108	101	{109,110,111,112}
250	103	165	{125}
283	130	101	{131,132}
284	100	101	{101,102}
285	103	101	{104,105}
286	138	101	{137,140,141,142,143,144,145,146,147,148,149,150,152}
288	113	101	{}
162	103	100	{104,105,124,125,127,128}
164	108	100	{109,110,111,112,121,122,123}
174	106	113	{107,117,119,118,120,133,134,139,135}
289	106	100	{107,117,118,119,120,133,134,135,139}
290	100	113	{101,102,151}
\.


SELECT setval('public.seq_menu', GREATEST((SELECT last_value FROM public.seq_menu), (SELECT COALESCE(MAX(menu_id), 98) + 2 FROM schema_utilisateur.tp_menu)), true);

SELECT setval('public.seq_profile', GREATEST((SELECT last_value FROM public.seq_profile), (SELECT COALESCE(MAX(pro_id), 98) + 2 FROM schema_utilisateur.tp_profile)), true);

SELECT setval('public.seq_pmsm', GREATEST((SELECT last_value FROM public.seq_pmsm), (SELECT COALESCE(MAX(pmsm_id), 98) + 2 FROM schema_utilisateur.tr_profilemenusousmenu)), true);

SELECT setval('public.seq_params_corgrade_pk', GREATEST((SELECT last_value FROM public.seq_params_corgrade_pk), (SELECT COALESCE(MAX(pmc_id), 98) + 2 FROM schema_utilisateur.tp_profile_menu_child)), true);
