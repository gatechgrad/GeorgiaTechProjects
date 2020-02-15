#include <stdio.h>




int increment ( void) {

   i++;

   return i;

}


int main (void) {
	int i;
	int j;
    for (j=0; j < 50; j++) {

		i = increment();
        printf("%d", i);


	}

    return 0;



}
