#include <SFML/Graphics.hpp>
#include <iostream>

class TileMap : public sf::Drawable, public sf::Transformable
{
public:

    bool load(const std::string& tileset, sf::Vector2u tileSize, const int* tiles, unsigned int width, unsigned int height)
    {
        // load the tileset texture
        if (!m_tileset.loadFromFile(tileset))
            return false;

        // resize the vertex array to fit the level size
        m_vertices.setPrimitiveType(sf::Quads);
        m_vertices.resize(width * height * 4);

        // populate the vertex array, with one quad per tile
        for (unsigned int i = 0; i < width; ++i)
            for (unsigned int j = 0; j < height; ++j)
            {
                // get the current tile number
                int tileNumber = tiles[i + j * width];

                // find its position in the tileset texture
                //int tu = tileNumber % (m_tileset.getSize().x / tileSize.x);
                //int tv = tileNumber / (m_tileset.getSize().x / tileSize.x);

                // get a pointer to the current tile's quad
                sf::Vertex* quad = &m_vertices[(i + j * width) * 4];

                // define its 4 corners
                quad[0].position = cartToIso(sf::Vector2f(i * tileSize.x, j * tileSize.y)) + sf::Vector2f(tileSize.x * width, 0);
                quad[1].position = cartToIso(sf::Vector2f((i + 1) * tileSize.x, j * tileSize.y)) + sf::Vector2f(tileSize.x * width, 0);
                quad[2].position = cartToIso(sf::Vector2f((i + 1) * tileSize.x, (j + 1) * tileSize.y)) + sf::Vector2f(tileSize.x * width, 0);
                quad[3].position = cartToIso(sf::Vector2f(i * tileSize.x, (j + 1) * tileSize.y)) + sf::Vector2f(tileSize.x * width, 0);

                // define its 4 texture coordinates
                /*quad[0].texCoords = cartToIso(sf::Vector2f(tu * tileSize.x, tv * tileSize.y)) - sf::Vector2f(0, tileSize.y / 2);
                quad[1].texCoords = cartToIso(sf::Vector2f((tu + 1) * tileSize.x, tv * tileSize.y)) - sf::Vector2f(0, tileSize.y / 2);
                quad[2].texCoords = cartToIso(sf::Vector2f((tu + 1) * tileSize.x, (tv + 1) * tileSize.y)) - sf::Vector2f(0, tileSize.y / 2);
                quad[3].texCoords = cartToIso(sf::Vector2f(tu * tileSize.x, (tv + 1) * tileSize.y)) - sf::Vector2f(0, tileSize.y / 2);*/

                quad[0].texCoords = sf::Vector2f(tileSize.x + ((tileSize.x * 2) * tileNumber), 0);
                quad[1].texCoords = sf::Vector2f((tileSize.x * 2) + ((tileSize.x * 2) * tileNumber), tileSize.y / 2);
                quad[2].texCoords = sf::Vector2f(tileSize.x + ((tileSize.x * 2) * tileNumber), tileSize.y);
                quad[3].texCoords = sf::Vector2f((tileSize.x * 2) * tileNumber, tileSize.y / 2);

                std::cout << "Coordenadas: " << std::endl;
                std::cout << "0 " << quad[0].texCoords.x << " " << quad[0].texCoords.y << std::endl;
                std::cout << "1 " << quad[1].texCoords.x << " " << quad[1].texCoords.y << std::endl;
                std::cout << "2 " << quad[2].texCoords.x << " " << quad[2].texCoords.y << std::endl;
                std::cout << "3 " << quad[3].texCoords.x << " " << quad[3].texCoords.y << std::endl;
            }

        return true;
    }

private:

    sf::Vector2f cartToIso(sf::Vector2f cart) {
        return sf::Vector2f(
            cart.x - cart.y,
            (cart.x + cart.y) / 2.0f
        );
    }

    sf::Vector2f isoToCart(sf::Vector2f iso) {
        return sf::Vector2f(
            (2.0f * iso.y + iso.x) / 2.0f,
            (2.0f * iso.y - iso.x) / 2.0f
        );
    }

    virtual void draw(sf::RenderTarget& target, sf::RenderStates states) const
    {
        // apply the transform
        states.transform *= getTransform();

        // apply the tileset texture
        states.texture = &m_tileset;

        // draw the vertex array
        target.draw(m_vertices, states);
    }

    sf::VertexArray m_vertices;
    sf::Texture m_tileset;
};
