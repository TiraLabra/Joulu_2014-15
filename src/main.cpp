#include <cstdlib> //srand
#include <curses.h>
#include <iostream>
#include <limits.h>
#include <setjmp.h>

#define GENDISTANCE 30
#define VIEWDISTANCE 15

#include "common.hpp"
#include "coord.hpp"
#include "qtree.hpp"
#include "list.hpp"
#include "queue.hpp"
#include "generator.hpp"

//sigsegv handling
#include <signal.h>
#ifdef SENSIBLE_OS
#include <execinfo.h>
#include <stdlib.h>
#include <unistd.h>
#endif

int mw, mh;
jmp_buf env;
WINDOW *win;
Qtree<char> data;
Qtree<bool> visible;
bool hax = false;
//#define DEBUG

#ifdef SENSIBLE_OS
void handle(int sig, siginfo_t *siginfo, void *context) {
#else
void handle(int sig) {
#endif
    longjmp(env, 1);
}

void init_handle() {
#ifdef SENSIBLE_OS
    struct sigaction act = {};
    act.sa_sigaction = &handle;
    act.sa_flags = SA_SIGINFO;
    if(sigaction(SIGSEGV, &act, NULL)) {
        std::cerr << "Failed to handle sigsegv." << std::endl;
        exit(1);
    }
    if(sigaction(SIGABRT, &act, NULL)) {
        std::cerr << "Failed to handle sigabrt." << std::endl;
        exit(1);
    }
#else
    signal(SIGSEGV, handle);
#endif
}

bool isVisible(const Coord &coord) {
    if(hax)
        return true;
    return visible.contains(coord) && visible.get(coord);
}

void generate(const Coord &coord) {
    List<Coord> points;
    points.add(coord);
    get_generator()(data, points, GENDISTANCE);
}

// make a tile visible
void visible_tile(const Coord &coord) {
    visible.add(true, coord);
    if(!data.contains(coord))
        generate(coord);
}

bool visible_line(const Coord &_a, const Coord &_b) {
    Coord a(_a);
    Coord b(_b);
    bool steep = ABS(b.y-a.y) > ABS(b.x-a.x);
    if(steep) {
        std::swap(a.x, a.y);
        std::swap(b.x, b.y);
    }
    if(a.x > b.x)
        std::swap(a, b);
    int dx = b.x - a.x;
    int dy = ABS(b.y - a.y);
    int err = dx / 2;
    int step = a.y < b.y ? 1 : -1;
    int y = a.y;
    for(int x = a.x; x <= b.x; x++) {
        Coord cur = steep ? Coord(y, x) : Coord(x, y);
        if((x != a.x && x != b.x) && data.get(cur, '#') == '#')
            return false;
        err -= dy;
        if(err < 0) {
            y += step;
            err += dx;
        }
    }
    return true;
}

void slow_vision(const Coord &coord) {
    for(int y = coord.y - VIEWDISTANCE; y <= coord.y + VIEWDISTANCE; y++)
        for(int x = coord.x - VIEWDISTANCE; x <= coord.x + VIEWDISTANCE; x++)
            if((x-coord.x)*(x-coord.x) + (y-coord.y)*(y-coord.y) < VIEWDISTANCE*VIEWDISTANCE)
                if(visible_line(coord, Coord(x, y)))
                    visible_tile(Coord(x, y));
}

void fov(const Coord &coord) {
    slow_vision(coord);
}

void drawchar(int x, int y, char c) {
    mvaddch(y, x*2, c);
    mvaddch(y, x*2+1, ' ');
}

//render screen
void render(const Coord &coord) {
#ifndef DEBUG
    int mx, my;
    int hx, hy;
    getmaxyx(win, my, mx);
    mx/=2;
    hx = mx/2;
    hy = my/2;
    for(int y = coord.y-hy; y < coord.y+hy; y++) {
        for(int x = coord.x-hx; x < coord.x+hx; x++)
            if(data.contains(Coord(x, y)) && isVisible(Coord(x, y)))
                drawchar(x-(coord.x-hx), y-(coord.y-hy), data.get(Coord(x, y)));
            else if(isVisible(Coord(x, y)))
                drawchar(x-(coord.x-hx), y-(coord.y-hy), ' ');
            else
                drawchar(x-(coord.x-hx), y-(coord.y-hy), ' ');
    }
    drawchar(coord.x-(coord.x-hx), coord.y-(coord.y-hy), '%');
    move(coord.y-(coord.y-hy), 2*(coord.x-(coord.x-hx)));
#else
    std::cout << coord.x << " " << coord.y << std::endl;
#endif
}

void path(Queue<Coord> &target, const Coord &a, const Coord &b) {
    if(a == b)
        return;
    Queue<std::pair<Coord, Coord> > queue;
    Qtree<Coord> prev;
    prev.add(a, a);
    queue.add(std::pair<Coord, Coord>(a + Coord(1, 0), a));
    queue.add(std::pair<Coord, Coord>(a + Coord(-1, 0), a));
    queue.add(std::pair<Coord, Coord>(a + Coord(0, 1), a));
    queue.add(std::pair<Coord, Coord>(a + Coord(0, -1), a));
    std::pair<Coord, Coord> c;
    while(queue.hasNext()) {
        c = queue.pop();
        if(data.get(c.first, '#') != '.')
            continue;
        if(prev.contains(c.first))
            continue;
        if(!visible.contains(c.first))
            continue;
        prev.add(c.second, c.first);
        if(c.first == b)
            goto found;
        queue.add(std::pair<Coord, Coord>(c.first + Coord(-1, 0), c.first));
        queue.add(std::pair<Coord, Coord>(c.first + Coord(1, 0), c.first));
        queue.add(std::pair<Coord, Coord>(c.first + Coord(0, -1), c.first));
        queue.add(std::pair<Coord, Coord>(c.first + Coord(0, 1), c.first));
    }
    std::cout << "not found" << std::endl;
    return;
found:
    Coord cur = b;
    List<Coord> stack;
    while(cur != a) {
        stack.add(cur);
        cur = prev.get(cur);
    }
    for(int i = 0; i < stack.size(); i++) {
        cur = stack.get(stack.size()-1-i);
        target.add(cur);
    }
}

int read_char() {
#ifndef DEBUG
    return getch();
#else
    int c = getchar();
    if(c == '\033') {
        c <<= 8;
        c |= getchar();
        c <<= 8;
        c |= getchar();
        c <<= 8;
        c |= getchar();
        switch((c>>8)&255) {
            case 'A':
                return KEY_UP;
            case 'B':
                return KEY_DOWN;
            case 'C':
                return KEY_RIGHT;
            case 'D':
                return KEY_LEFT;
        }
    }
    return c;
#endif
}

void set_timeout(int ms) {
#ifndef DEBUG
    timeout(ms);
#endif
}

void init_ncurses() {
#ifndef DEBUG
    win = initscr();
    keypad(win, TRUE);
    timeout(-1);
    noecho();
#endif
}

void clean_ncurses() {
#ifndef DEBUG
    keypad(win, FALSE);
    echo();
    endwin();
#endif
}


int main() {
    bool localvision = false;
    srand((unsigned)time(NULL));
    init_handle();
    generate(Coord(0, 0));

    init_ncurses();

    mw = data.width();
    mh = data.height();
    Coord plr;
    if(!setjmp(env)) {
        while(true) {
            if(localvision)
                visible = Qtree<bool>();
            fov(plr);
            render(plr);
            int input = read_char();
            Coord newpos = plr;
            switch(input) {
                case KEY_UP:
                    newpos += Coord(0, -1);
                    break;
                case KEY_LEFT:
                    newpos += Coord(-1, 0);
                    break;
                case KEY_DOWN:
                    newpos += Coord(0, 1);
                    break;
                case KEY_RIGHT:
                    newpos += Coord(1, 0);
                    break;
                case 'p':
                    hax = !hax;
                    break;
                case 'l':
                    localvision = !localvision;
                    break;
                case 'q':
                    goto end;
                case 'g':
                    Queue<Coord> tmp;
                    path(tmp, plr, Coord(0, 0));
                    set_timeout(30);
                    while(tmp.hasNext()) {
                        read_char();
                        newpos = tmp.pop();
                        render(newpos);
                    }
                    plr = newpos;
                    set_timeout(-1);
                    break;
            }
            if(data.contains(newpos) && data.get(newpos) == '.')
                plr = newpos;
        }
    }
end:
    clean_ncurses();
    return 0;
}
