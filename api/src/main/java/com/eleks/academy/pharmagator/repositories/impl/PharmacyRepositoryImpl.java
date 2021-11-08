package com.eleks.academy.pharmagator.repositories.impl;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.projections.PharmacyLight;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PharmacyRepositoryImpl implements PharmacyRepository {

    private final Connection connection;

    @Override
    public List<Pharmacy> findAll() {
        ArrayList<Pharmacy> pharmacies = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM pharmacies;")) {
            while (resultSet.next()) {
                Pharmacy pharmacy = new Pharmacy();
                pharmacy.setId(resultSet.getLong("id"));
                pharmacy.setName(resultSet.getString("name"));
                pharmacy.setMedicineLinkTemplate(resultSet.getString("medicine_link_template"));

                pharmacies.add(pharmacy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pharmacies;
    }

    @Override
    public Optional<Pharmacy> findById(Long id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM pharmacies WHERE id = ?;")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Pharmacy pharmacy = new Pharmacy();
                pharmacy.setId(resultSet.getLong("id"));
                pharmacy.setName(resultSet.getString("name"));
                pharmacy.setMedicineLinkTemplate(resultSet.getString("medicine_link_template"));

                return Optional.of(pharmacy);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Pharmacy save(Pharmacy pharmacy) {
        Long id = pharmacy.getId();
        long generatedId;
        if (id == null || id == 0) {
            generatedId = insert(pharmacy);
        } else {
            Optional<Pharmacy> optionalPharmacy = findById(id);
            if (optionalPharmacy.isPresent()) {
                update(pharmacy);
                generatedId = pharmacy.getId();
            } else {
                generatedId = insert(pharmacy);
            }
        }
        return findById(generatedId).orElseThrow(() -> new RuntimeException("Not found"));
    }

    private void update(Pharmacy fromRequest) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE pharmacies SET name = ?, medicine_link_template = ? WHERE id = ?;")) {
            statement.setString(1, fromRequest.getName());
            statement.setString(2, fromRequest.getMedicineLinkTemplate());
            statement.setLong(3, fromRequest.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private long insert(Pharmacy pharmacy) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO pharmacies (name, medicine_link_template) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, pharmacy.getName());
            statement.setString(2, pharmacy.getMedicineLinkTemplate());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
            throw new RuntimeException("Something went wrong");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM pharmacies  WHERE id = ?;")) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PharmacyLight> findAllLight() {
        return null;
    }
}
