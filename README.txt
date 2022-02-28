# Calin Teodor-Georgian
# 324CA

Pentru accesarea bazei de date mai eficient am utilizat HashMap de tip <username, User>, <title, Movie>, <title, Serial>. Astfel, cunoscand doar numele/titlul, se poate accesa obiectul cautat. De asemenea, am folosit un HashMap de tip <Genre, Integer> in care se pastreza numarul total de vizualizari ale video-urilor din genul respectiv. 

--- COMMAND ---
Fiecare comanda este implementata printr-o metoda in clasa User (user-ul fiind practic cel care da aceste comenzi ce aduc urmari asupra sa si asupra show-ului catre care se indreapta comanda).

** View ** : Video-ul se cauta in istoricul userului. In cazul in care nu se gaseste, se adauga titlul filmului in istoric, cu numar de vizualizari 1, iar daca se gaseste, atunci se mareste numarul de vizualizari din istoric cu 1.
De asemenea, se mareste cu 1 numarul de vizualizari totale ale video-ului respectiv, si automat si numarul de vizualizari ale fiecarui gen din care face parte video-ul.

** Favorite ** : Video-ul se cauta in istoricul userului. In cazul in care nu se gaseste, operatia de adaugare la lista de favortie nu poate fi efectuata. Daca acesta se gaseste in istoric, atunci se cauta si in lista de favorite a user-ului. In cazul in care nu se afla deja in liste de favorite, atunci acesta se adauga, iar numarul total de aparitii in listele de favorite a video-ului se mareste cu 1.

** Rate movie ** : Filmul se cauta in istoricul userului. In cazul in care nu se gaseste, operatia de rating nu poate fi efectuata. Daca acesta se gaseste, se cauta si in lista de filme carora li s-a dat rating de catre acel user. Daca se gaseste, operatia nu poate fi efectuata, iar daca nu se gaseste, atunci filmul este adaugat la aceasta lista, iar nota data se adauga in lista de rating-uri a filmului.

** Rate serial ** : Serialul se cauta in istoricul userului. In cazul in care nu se gaseste, operatia de rating nu poate fi efectuata. Daca acesta se gaseste, sezonul se cauta in lista sezoanelor carora li s-a dat rating de catre acel user. Daca se gaseste, operatia nu poate fi efectuata, iar daca nu se gaseste, atunci sezonul este adaugat la aceasta lista, iar nota data se adauga in lista de rating-uri a sezonului.


--- QUERY ---
Fiecare query este implementat printr-o metoda statica in clasele Video, User si Actor.

# Pentru actori #
** Average ** : Se creeaza o lista de actori, in care se adauga toti actorii cu rating diferit de 0, se sorteaza dupa rating si se creeaza un string cu numele primilor "n" actori din lista, de la stanga sau de la dreapta.

** Awards ** : Se creeaza o lista de actori, in care se adauga toti actorii cu premiile date ca filtre , se sorteaza dupa numarul total de premii si se creeaza un string cu numele primilor "n" actori din lista, de la stanga sau de la dreapta.

** Filter description ** : Se creeaza o lista de actori, in care se adauga toti actorii in descrierea carora se gasesc cuvintele date ca filtre, se sorteaza dupa nume si se creeaza un string cu numele primilor "n" actori din lista, de la stanga sau de la dreapta.

# Pentru video # (La apelarea functiei se va da ca parametru lista de filme sau de seriale)
Pentru toate cele 4 cazuri (longest, favorite, best_rated, most_viewed) se creeaza o lista de video-uri, in care se adauga video-urile care trec de filtrele "year" si "genre". Se sorteaza in functie de caz (durata, numar de aparitii in listele de favorite, rating sau numar de vizualizari), apoi se creeaza un string cu titlurile primelor "n" video-uri din lista, crescator sau descrescator.

# Pentru useri #
Se creeaza o lista de user-i in care se adauga doar user-ii care au adaugat cel putin un rating, se sorteaza dupa numarul de rating-uri date de user-i in total, apoi se creeaza un string cu username-ul primilor "n" user-i din lista, crescator sau descrescator.


--- RECOMMEND ---
Fiecare recomandare  este implementata printr-o metoda in clasa User.
Pentru recomandarile Popular, Favorite si Search se verifica mai intai daca userul este "premium".

** Standard ** : Se parcurge lista de filme, iar daca se gaseste un film nevazut de catre user, atunci intoarce titlul acestuia. Se procedeaza la fel si cu lista de seriale. Daca la final nu s-a gasit nici un video nevazut, se afiseaza un mesaj de eroare.

** Favorite ** : Se creeaza o lista de video-uri in care se adauga toate filmele si serialele nevazute de catre user. Se sorteaza dupa numarul de aparitii in listele de favorite si se afiseaza primul din lista.

** Best unseen ** : Se creeaza o lista de video-uri in care se adauga toate filmele si serialele nevazute de catre user. Se sorteaza dupa rating si se afiseaza primul din lista.

** Popular ** : Se creeaza o lista de genuri, care se sorteaza dupa numarul de vizualizari totale ale genului. Se cauta in lista de filme, si apoi in lista de seriale, un video nevazut de user si care sa apartina primului gen din lista de genuri. Daca se gaseste, se returneaza titlul sau, iar daca nu, se repeta procesul pentru urmatoarele genuri din lista.

** Search ** : Se creeaza o lista de video-uri in care se adauga toate filmele si serialele nevazute de catre user si apartinand genului cerut. Se sorteaza dupa rating si se afiseaza titlurile video-urilor din lista.


















