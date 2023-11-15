DROP TABLE IF EXISTS artists;

CREATE TABLE IF NOT EXISTS artists (
    artistid INT(11) NOT NULL AUTO_INCREMENT,
    surname VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    nationality VARCHAR(255) NOT NULL,
    yob SMALLINT(6) NOT NULL,
    biography LONGTEXT NOT NULL,
    PRIMARY KEY (artistid)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO artists (artistid, surname, firstname, nationality, yob, biography) VALUES
(1, 'Van Gogh', 'Vincent', 'Netherlands', 1853, 'Vincent Willem van Gogh was a Dutch Post-Impressionist painter who is among the most famous and influential figures in the history of Western art. In just over a decade he created approximately 2100 artworks, including around 860 oil paintings, most of them in the last two years of his life. They include landscapes, still lifes, portraits and self-portraits, and are characterized by bold, symbolic colors, and dramatic, impulsive and highly expressive brushwork that contributed to the foundations of modern art. Only one of his paintings was known by name to have been sold during his lifetime. Van Gogh became famous after his suicide, aged 37, which followed years of poverty and mental illness'),
(2, 'di Lodovico Buonarroti Simoni', 'Michelangelo', 'Florence', 1475, 'Michelangelo di Lodovico Buonarroti Simoni known as Michelangelo, was an Italian sculptor, painter, architect, and poet of the High Renaissance. Born in the Republic of Florence, his work was inspired by models from classical antiquity and had a lasting influence on Western art. Michelangelos creative abilities and mastery in a range of artistic arenas define him as an archetypal Renaissance man, along with his rival and elder contemporary, Leonardo da Vinci. Given the sheer volume of surviving correspondence, sketches, and reminiscences, Michelangelo is one of the best-documented artists of the 16th century. He was lauded by contemporary biographers as the most accomplished artist of his era');


DROP TABLE IF EXISTS artworks;

CREATE TABLE IF NOT EXISTS artworks (
    artworkid INT(11) NOT NULL AUTO_INCREMENT,
    artistid INT(11) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description LONGTEXT NOT NULL,
    medium VARCHAR(255) NOT NULL,
    imagename VARCHAR(255) NOT NULL,
    PRIMARY KEY (artworkid),
    FOREIGN KEY (artistid) REFERENCES artists(artistid)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO artworks (artworkid, artistid, title, description, medium, imagename)
VALUES
(1, 1, 'Sunflowers', 'Sunflowers is the title of two series of still life paintings by the Dutch painter Vincent van Gogh. The first series, executed in Paris in 1887, depicts the flowers lying on the ground, while the second set, made a year later in Arles, shows a bouquet of sunflowers in a vase. In the artists mind, both sets were linked by the name of his friend Paul Gauguin, who acquired two of the Paris versions.', 'Oil on canvas', 'sunflower'),
(2, 1, 'Self-Portrait', 'In 1886 Vincent van Gogh left his native Holland and settled in Paris, where his beloved brother Theo was a dealer in paintings. Van Gogh created at least twenty-four self-portraits during his two-year stay in the energetic French capital. This early example is modest in size and was painted on prepared artist’s board rather than canvas. Its densely dabbed brushwork, which became a hallmark of Van Gogh’s style, reflects the artist’s response to Georges Seurat’s revolutionary pointillist technique in A Sunday on La Grande Jatte—1884', 'Oil on artist’s board, mounted on cradled panel', 'selfportrait'),
(3, 2, 'Sistine chapel ceiling', 'Michelangelo painted the ceiling of the Sistine Chapel,[54] which took approximately four years to complete (1508–1512).[53] According to Condivis account, Bramante, who was working on the building of St. Peters Basilica, resented Michelangelos commission for the popes tomb and convinced the pope to commission him in a medium with which he was unfamiliar, in order that he might fail at the task.', 'Fresco', 'sistine'),
(4, 2, 'The Last Judgement', 'Shortly before his death in 1534, Pope Clement VII commissioned Michelangelo to paint a fresco of The Last Judgment on the altar wall of the Sistine Chapel. His successor, Pope Paul III, was instrumental in seeing that Michelangelo began and completed the project, which he labored on from 1534 to October 1541.[53] The fresco depicts the Second Coming of Christ and his Judgement of the souls. Michelangelo ignored the usual artistic conventions in portraying Jesus, showing him as a massive, muscular figure, youthful, beardless and naked', 'Fresco', 'lastjudgement');