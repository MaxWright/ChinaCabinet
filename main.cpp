#include <iostream>

template <typename T>
class Base
{
public:
    void doSomething()
    {
        T& derived = static_cast<T&>(*this);
        std::cout << "Base" << a++ << std::endl;
        //derived.doSomething();
        //use derived...
    }
    //Base(){};
private:
    int a = 0;
    //Base(){};
    friend T;
};

class Derived : public Base<Derived>
{
public:
    Derived() {

    }
//    void doSomething() {
//        std::cout << "class Derived : public Base<Derived>" << std::endl;
//    }
};


class Derived1 : public Base<Derived1>
{
public:
    Derived1() {

    }
    void doSomething() {
        std::cout << "class Derived1 : public Base<Derived1>" << std::endl;
    }
};

class Derived2 : public Base<Derived1> // bug in this line of code
{
public:
    Derived2() {

    }
    void doSomething() {
        std::cout << "class Derived2 : public Base<Derived1>" << std::endl;
    }
};

class Derived3 : public Base<Derived1> // bug in this line of code
{
public:
    Derived3() {

    }
};

template <typename T>
struct NumericalFunctions
{
    void scale(double multiplicator)
    {
        T& underlying = static_cast<T&>(*this);
        underlying.setValue(underlying.getValue() * multiplicator);
    }
    void square()
    {
        T& underlying = static_cast<T&>(*this);
        underlying.setValue(underlying.getValue() * underlying.getValue());
    }
    void setToOpposite()
    {
        scale(-1);
    };
};

class Sensitivity : public NumericalFunctions<Sensitivity>
{
public:
    double getValue() const {
        return 3;
    }
    void setValue(double value) {

    }
    // rest of the sensitivity's rich interface...
};

int main() {
    std::cout << "Hello, World!" << std::endl;
    Derived d;
    Derived dd;
    Derived1 d1;
    Derived2 d2;
    Derived3 d3;
    d.doSomething();
    dd.doSomething();
    d1.doSomething();
    d2.doSomething();
    d3.doSomething();

    Sensitivity s;
    s.scale(-1);
    return 0;
}