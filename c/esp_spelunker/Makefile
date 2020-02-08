EXEC = s.exe
ALLEGRO_DIR = D:/djgpp/allegro

$(EXEC): s.o title.o map.o monster.o item.o
	gcc -s -o $(EXEC) s.o map.o monster.o title.o item.o ../../djgpp/allegro/lib/djgpp/LIBALLEG.A

s.o:	s.c s.h
	gcc -I$(ALLEGRO_DIR) -I$(ALLEGRO_DIR)/src -I$(ALLEGRO_DIR)/obj/djgpp -Wall -Wno-unused -m486 -O3 -ffast-math -fomit-frame-pointer -o s.o -c s.c

title.o:	title.c
	gcc -I$(ALLEGRO_DIR) -I$(ALLEGRO_DIR)/src -I$(ALLEGRO_DIR)/obj/djgpp -Wall -Wno-unused -m486 -O3 -ffast-math -fomit-frame-pointer -o title.o -c title.c

map.o:	map.c
	gcc -I$(ALLEGRO_DIR) -I$(ALLEGRO_DIR)/src -I$(ALLEGRO_DIR)/obj/djgpp -Wall -Wno-unused -m486 -O3 -ffast-math -fomit-frame-pointer -o map.o -c map.c

monster.o:	monster.c
	gcc -I$(ALLEGRO_DIR) -I$(ALLEGRO_DIR)/src -I$(ALLEGRO_DIR)/obj/djgpp -Wall -Wno-unused -m486 -O3 -ffast-math -fomit-frame-pointer -o monster.o -c monster.c

item.o:	item.c
	gcc -I$(ALLEGRO_DIR) -I$(ALLEGRO_DIR)/src -I$(ALLEGRO_DIR)/obj/djgpp -Wall -Wno-unused -m486 -O3 -ffast-math -fomit-frame-pointer -o item.o -c item.c

clean:
	del *.o
	del $(EXEC)

all:
	gcc -I$(ALLEGRO_DIR) -I$(ALLEGRO_DIR)/src -I$(ALLEGRO_DIR)/obj/djgpp -Wall -Wno-unused -m486 -O3 -ffast-math -fomit-frame-pointer -o s.o -c s.c
	gcc -I$(ALLEGRO_DIR) -I$(ALLEGRO_DIR)/src -I$(ALLEGRO_DIR)/obj/djgpp -Wall -Wno-unused -m486 -O3 -ffast-math -fomit-frame-pointer -o map.o -c map.c
	gcc -I$(ALLEGRO_DIR) -I$(ALLEGRO_DIR)/src -I$(ALLEGRO_DIR)/obj/djgpp -Wall -Wno-unused -m486 -O3 -ffast-math -fomit-frame-pointer -o monster.o -c monster.c
	gcc -I$(ALLEGRO_DIR) -I$(ALLEGRO_DIR)/src -I$(ALLEGRO_DIR)/obj/djgpp -Wall -Wno-unused -m486 -O3 -ffast-math -fomit-frame-pointer -o title.o -c title.c
	gcc -I$(ALLEGRO_DIR) -I$(ALLEGRO_DIR)/src -I$(ALLEGRO_DIR)/obj/djgpp -Wall -Wno-unused -m486 -O3 -ffast-math -fomit-frame-pointer -o item.o -c item.c
	gcc -s -o $(EXEC) s.o map.o monster.o title.o item.o $(ALLEGRO_DIR)/lib/djgpp/LIBALLEG.A
