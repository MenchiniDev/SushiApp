create database if not exists `lorenzo_menchini_615580`;


DROP TABLE IF EXISTS `OrdinazioniPossibili`;
CREATE TABLE `OrdinazioniPossibili` (
  `Nome` varchar(50) not null,
  `Numero` int auto_increment,
  `costo` decimal  default 0,
  PRIMARY KEY (`Numero`) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Ordinazioni`;
CREATE TABLE `Ordinazioni` (
  `NomeUtente` varchar(50) NOT NULL,
  `Ordinazione` varchar(50) NOT NULL, /*inserisco ogni singola portata*/
  `numero` int default 0,
  `costo` int default 0,
  `portate` int default 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `OrdinazioniStorico`;
CREATE TABLE `OrdinazioniStorico` (
  `NomeUtente` varchar(50) NOT NULL,
  `Ordinazione` varchar(50) NOT NULL, /*inserisco ogni singola portata*/
  `Quantita` int NOT NULL, /*con relativa quantità*/
  `costo` int default 0,
  `portate` int default 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Login`;
CREATE TABLE `Login` (
  `NomeUtente` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`NomeUtente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `recensioni`;
CREATE TABLE `recensioni` (
  `NomeUtente` varchar(50) NOT NULL,
  `Ordinazione` varchar(50) NOT NULL, 
  `voto` text(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into OrdinazioniPossibili values
("Nighiri",1,3);
insert into OrdinazioniPossibili values
("hosomaki",2,3);
insert into OrdinazioniPossibili values
("uramaki",3,3);
insert into OrdinazioniPossibili values
("temaki",4,3);
insert into OrdinazioniPossibili values
("futomaki",5,3);
insert into OrdinazioniPossibili values
("california rolls",6,3);
insert into OrdinazioniPossibili values
("chirashi",7,3);
insert into OrdinazioniPossibili values
("donburi",8,3);
insert into OrdinazioniPossibili values
("gunkan",9,3);
insert into OrdinazioniPossibili values
("oshi sushi",10,3);
insert into OrdinazioniPossibili values
("onigiri",11,3);
insert into OrdinazioniPossibili values
("udon",12,3);
insert into OrdinazioniPossibili values
("Involtini di primavera semplici",13,2);
insert into OrdinazioniPossibili values
("Involtini di primavera con verdura",14,2);
insert into OrdinazioniPossibili values
("Involtini di primavera con carne",15,2);
insert into OrdinazioniPossibili values
("brioche fritta di pollo",16,3.50);
insert into OrdinazioniPossibili values
("edamame fritto",18,3);
insert into OrdinazioniPossibili values
("cockatil di gamberi",19,5);
insert into OrdinazioniPossibili values
("wakame",20,3);
insert into OrdinazioniPossibili values
("insalata di pollo salato",21,3);
insert into OrdinazioniPossibili values
("insalata di gamberi salati",22,3);
insert into OrdinazioniPossibili values
("insalata di pesce misto",23,3);
insert into OrdinazioniPossibili values
("ravioli di carne al vapore",24,3);
insert into OrdinazioniPossibili values
("ravioli di carne alla griglia",25,3);
insert into OrdinazioniPossibili values
("shaoMai",26,3);
insert into OrdinazioniPossibili values
("ravioli di verdure",27,3);
insert into OrdinazioniPossibili values
("ravioli cantonese",28,3);
insert into OrdinazioniPossibili values
("pane cinese",29,3);
insert into OrdinazioniPossibili values
("zuppa agropiccante",30,3);
insert into OrdinazioniPossibili values
("zuppa miso",31,3);
insert into OrdinazioniPossibili values
("zuppa di granchio e asparagi",32,3);
insert into OrdinazioniPossibili values
("spaghetti di riso con verdure",33,3);
insert into OrdinazioniPossibili values
("spaghetti di riso con verdure e carne",34,3);
insert into OrdinazioniPossibili values
("spaghetti di riso con frutti di mare e verdure",35,3);
insert into OrdinazioniPossibili values
("spaghetti di soia con verdure e carne mista",36,3);
insert into OrdinazioniPossibili values
("spaghetti di soia con frutti di mare e verdure",37,3);
insert into OrdinazioniPossibili values
("yaki udon",38,3);
insert into OrdinazioniPossibili values
("yaki udon con frutti di mare",39,3);
insert into OrdinazioniPossibili values
("yaki soba",40,3);
insert into OrdinazioniPossibili values
("yaki soba con frutti di mare",41,3);
insert into OrdinazioniPossibili values
("ramen con manzo e verdure",42,3);
insert into OrdinazioniPossibili values
("ramen con frutti di mare e verdure",43,3);
insert into OrdinazioniPossibili values
("riso cantonese",44,3);
insert into OrdinazioniPossibili values
("riso con frutti di mare",45,3);
insert into OrdinazioniPossibili values
("riso con salmone",46,3);
insert into OrdinazioniPossibili values
("maiale agrodolce",47,3);
insert into OrdinazioniPossibili values
("manzo con funghi e bambu",48,3);
insert into OrdinazioniPossibili values
("manzo con salsa piccante",49,3);
insert into OrdinazioniPossibili values
("manzo gonbao",50,3);
insert into OrdinazioniPossibili values
("gamberi funghi e bambu ",51,3);
insert into OrdinazioniPossibili values
("gamberi con salsa piccante",52,3);
insert into OrdinazioniPossibili values
("gamberi al limone",53,3);
insert into OrdinazioniPossibili values
("gamberi al limone",54,3);
insert into OrdinazioniPossibili values
("gamberi fritti",55,3);
insert into OrdinazioniPossibili values
("gamberi sale e pepe",56,3);
insert into OrdinazioniPossibili values
("anatra fritta",57,3);
insert into OrdinazioniPossibili values
("anatra all'arancia",58,3);
insert into OrdinazioniPossibili values
("scodella di verdure",60,3);
insert into OrdinazioniPossibili values
("scodella di manzo",61,3);
insert into OrdinazioniPossibili values
("scodella di gamberi",62,3);
insert into OrdinazioniPossibili values
("scodella di frutti di mare",63,3);
insert into OrdinazioniPossibili values
("germogli di soia",64,3);
insert into OrdinazioniPossibili values
("spiedini di pollo",65,3);
insert into OrdinazioniPossibili values
("spiedini di gamber",66,3);
insert into OrdinazioniPossibili values
("gamberoni alla griglia",67,3);
insert into OrdinazioniPossibili values
("salmone alla griglia",68,3);
insert into OrdinazioniPossibili values
("branzino alla griglia",69,3);
insert into OrdinazioniPossibili values
("tataki salmone",70,3);
insert into OrdinazioniPossibili values
("tataki tonno",71,3);
insert into OrdinazioniPossibili values
("sake tempura",72,3);
insert into OrdinazioniPossibili values
("tranci di tonno grigliato",73,3);
insert into OrdinazioniPossibili values
("salmone sakura",74,3);
insert into OrdinazioniPossibili values
("tempura di verdure",75,3);
insert into OrdinazioniPossibili values
("tempura di gamberi",76,3);
insert into OrdinazioniPossibili values
("tempura di calamari",77,3);
insert into OrdinazioniPossibili values
("sake mono ",78,3);
insert into OrdinazioniPossibili values
("chips di patate con salmone",79,3);
insert into OrdinazioniPossibili values
("uramaki misto",80,3);
insert into OrdinazioniPossibili values
("sushi sashimi",81,3);
insert into OrdinazioniPossibili values
("onighiri salmone",82,3);
insert into OrdinazioniPossibili values
("onighiri tonno",84,3);
insert into OrdinazioniPossibili values
("onighiri semplice",85,3);
insert into OrdinazioniPossibili values
("nighiri salmone",86,3);
insert into OrdinazioniPossibili values
("nighiri salmone affumicato",87,3);
insert into OrdinazioniPossibili values
("nighiri tonno",88,3);
insert into OrdinazioniPossibili values
("nighiri anago",89,3);
insert into OrdinazioniPossibili values
("nighiri granchio",90,3);
insert into OrdinazioniPossibili values
("nighiri branzino",91,3);
insert into OrdinazioniPossibili values
("nighiri polpo",92,3);
insert into OrdinazioniPossibili values
("hoosmaki tonno",93,3);
insert into OrdinazioniPossibili values
("hoosmaki gamberi",94,3);
insert into OrdinazioniPossibili values
("hoosmaki avocado",95,3);
insert into OrdinazioniPossibili values
("hoosmaki cetrioli",96,3);
insert into OrdinazioniPossibili values
("hoosmaki anguilla",97,3);
insert into OrdinazioniPossibili values
("temaki salmone e avocado",98,3);
insert into OrdinazioniPossibili values
("temaki tonno e avocado",99,3);
insert into OrdinazioniPossibili values
("temaki gamberi tempura con insalata e maionese",100,3);
insert into OrdinazioniPossibili values
("temaki cetrioli avocado lattuga e maionese",101,3);
insert into OrdinazioniPossibili values
("temaki spicy salmon",102,3);
insert into OrdinazioniPossibili values
("temaki spicy tonno",103,3);
insert into OrdinazioniPossibili values
("uramaky salmone",104,3);
insert into OrdinazioniPossibili values
("uramaky sake ",105,3);
insert into OrdinazioniPossibili values
("uramaky tekka",106,3);
insert into OrdinazioniPossibili values
("uramaky ebi",107,3);
insert into OrdinazioniPossibili values
("uramaky california",108,3);
insert into OrdinazioniPossibili values
("uramaky philadelfia salmone",109,3);
insert into OrdinazioniPossibili values
("uramaky miu ",110,3);
insert into OrdinazioniPossibili values
("uramaky philadelphia ebi",111,3);
insert into OrdinazioniPossibili values
("uramaky spicy salmon",112,3);
insert into OrdinazioniPossibili values
("uramaky spicy tuna",113,3);
insert into OrdinazioniPossibili values
("uramaky futo maky",114,3);
insert into OrdinazioniPossibili values
("uramaky maky fritto",115,3);
insert into OrdinazioniPossibili values
("uramaky fritto mini",116,3);
insert into OrdinazioniPossibili values
("uramaky vegetariano",117,3);
insert into OrdinazioniPossibili values
("uramaky rinball",118,3);
insert into OrdinazioniPossibili values
("uramaky salmon out",119,3);
insert into OrdinazioniPossibili values
("uramaky crazy salmon",120,3);
insert into OrdinazioniPossibili values
("uramaky ebi ten",121,3);
insert into OrdinazioniPossibili values
("uramaky tiger roll",122,3);
insert into OrdinazioniPossibili values
("uramaky cheese roll",123,3);
insert into OrdinazioniPossibili values
("uramaky tartare roll",124,3);
insert into OrdinazioniPossibili values
("uramaky riso nero salmone e avocado",125,3);
insert into OrdinazioniPossibili values
("uramaky riso nero ebiten",126,3);
insert into OrdinazioniPossibili values
("uramaky sake arancini",127,3);
insert into OrdinazioniPossibili values
("uramaky white salmone",128,3);
insert into OrdinazioniPossibili values
("uramaky white ebi",129,3);
insert into OrdinazioniPossibili values
("uramaky pinky roll",130,3);
insert into OrdinazioniPossibili values
("uramaky orange",131,3);
insert into OrdinazioniPossibili values
("hoosmaki salmone",132,3);