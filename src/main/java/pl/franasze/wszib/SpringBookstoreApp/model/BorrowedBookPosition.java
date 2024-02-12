package pl.franasze.wszib.SpringBookstoreApp.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity(name = "torderposition")
public class BorrowedBookPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Book book;
    private int quantity;

    public BorrowedBookPosition(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }
}
